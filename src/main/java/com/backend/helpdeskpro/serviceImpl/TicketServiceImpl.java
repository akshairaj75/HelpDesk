package com.backend.helpdeskpro.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketCreateDto;
import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.entity.Category;
import com.backend.helpdeskpro.entity.Department;
import com.backend.helpdeskpro.entity.SlaPolicy;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.entity.TicketAttachment;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.enums.NotificationType;
import com.backend.helpdeskpro.enums.TicketStatus;
import com.backend.helpdeskpro.enums.UserRole;
import com.backend.helpdeskpro.repository.CategoryRepository;
import com.backend.helpdeskpro.repository.DepartmentRepository;
import com.backend.helpdeskpro.repository.SlaPolicyRepository;
import com.backend.helpdeskpro.repository.TicketAttachmentRepository;
import com.backend.helpdeskpro.repository.TicketRepository;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.NotificationService;
import com.backend.helpdeskpro.service.TicketService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class TicketServiceImpl implements TicketService {

        @Autowired
        UserRepository userRepository;

        @Autowired
        SlaPolicyRepository slaPolicyRepository;

        @Autowired
        CategoryRepository categoryRepository;

        @Autowired
        DepartmentRepository departmentRepository;

        @Autowired
        TicketRepository ticketRepository;

        @Autowired
        FileStorageService fileStorageService;

        @Autowired
        TicketAttachmentRepository attachmentRepository;

        @Autowired
        NotificationService notificationService;

        @Autowired
        AuditServiceImpl auditLogService;

        private String generateTicketNumber() {

                String datePart = LocalDate.now()
                                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));

                long countToday = ticketRepository.countByCreatedAtBetween(
                                LocalDate.now().atStartOfDay(),
                                LocalDate.now().plusDays(1).atStartOfDay());

                return String.format("TK-%s-%04d", datePart, countToday + 1);
        }

        @Transactional
        @Override
        public TicketResponseDto createTicket(
                        CustomUserPrincipal authUser,
                        TicketCreateDto dto,
                        List<MultipartFile> files,
                        HttpServletRequest request) {

                User currentUser = userRepository.findById(authUser.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                SlaPolicy slaPolicy = slaPolicyRepository.findById(dto.getSlaPolicyId())
                                .orElseThrow(() -> new RuntimeException("SLA Policy not found"));

                Category category = categoryRepository.findById(dto.getCategoryId())
                                .orElseThrow(() -> new RuntimeException("Category not found"));

                Department department = departmentRepository.findById(dto.getDepartmentId())
                                .orElseThrow(() -> new RuntimeException("Department not found"));
                Ticket ticket = new Ticket();

                ticket.setReporter(currentUser);
                ticket.setSlaPolicy(slaPolicy);
                ticket.setCategory(category);
                ticket.setDepartment(department);
                ticket.setSubject(dto.getSubject());
                ticket.setDescription(dto.getDescription());
                ticket.setPriority(dto.getPriority());
                ticket.setChannel(dto.getChannel());
                ticket.setSlaDueAt(
                                LocalDateTime.now().plusMinutes(slaPolicy.getResolutionMinutes())
                                                .plusMinutes(slaPolicy.getEscalationMinutes()));

                ticket.setTicketNo(generateTicketNumber());

                Ticket savedTicket = ticketRepository.save(ticket);

                // CREATING AUDITING LOG
                auditLogService.logAction(
                                "TICKET",
                                savedTicket.getId(),
                                savedTicket.getReporter(),
                                AuditAction.CREATED,
                                Map.of(
                                                "ticketNo", savedTicket.getTicketNo(),
                                                "newStatus", savedTicket.getStatus(),
                                                "subject", savedTicket.getSubject(),
                                                "priority", savedTicket.getPriority().name(),
                                                "status", savedTicket.getStatus()),
                                request);

                if (savedTicket.getAssignee() != null
                                && !savedTicket.getAssignee().getId().equals(authUser.getUserId())) {
                        User receiver = savedTicket.getAssignee();
                        notificationService.createNotification(
                                        receiver,
                                        ticket,
                                        NotificationType.TICKET_ASSIGNED,
                                        "Ticket Assigned",
                                        "You have been assigned a new ticket",
                                        "/tickets/" + ticket.getId());
                }

                if (files != null && !files.isEmpty()) {
                        for (MultipartFile file : files) {

                                String filePath;
                                TicketAttachment attachment = new TicketAttachment();

                                attachment.setTicket(savedTicket);
                                attachment.setUploadedBy(currentUser);
                                try {
                                        filePath = fileStorageService.storeFile(file, "attachment");
                                        attachment.setFileName(file.getOriginalFilename());
                                        attachment.setFileUrl(filePath);
                                        attachment.setFileSizeKb((int) (file.getSize() / 1024));
                                        attachment.setMimeType(file.getContentType());
                                        attachmentRepository.save(attachment);
                                        ticket.getAttachments().add(attachment);
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }

                return TicketResponseDto.fromEntity(ticket);

        }

        @Override
        public List<TicketResponseDto> getAllTickets(CustomUserPrincipal authUser) {

                if (authUser.getUser().getRole() == UserRole.STAFF) {
                        List<Ticket> tickets = ticketRepository.findByAssignee(authUser.getUser());
                        return tickets.stream()
                                        .map(TicketResponseDto::fromEntity)
                                        .toList();
                } else if (authUser.getUser().getRole() == UserRole.AGENT) {
                        // List<Ticket> tickets = ticketRepository.findByDepartment(authUser.getUser().getDepartment());
                        List<Ticket> tickets = ticketRepository.findByAssignee(authUser.getUser());
                        // List<Ticket> tickets = ticketRepository.findByAssignee(authUser.getUser());
                        return tickets.stream()
                                        .map(TicketResponseDto::fromEntity)
                                        .toList();
                } else if (authUser.getUser().getRole() == UserRole.TEAM_LEAD || authUser.getUser().getRole() == UserRole.ADMIN) {
                        List<Ticket> tickets = ticketRepository.findAll();
                        return tickets.stream()
                                        .map(TicketResponseDto::fromEntity)
                                        .toList();
                }
                throw new RuntimeException("You don't have permission to access this resource");

        }

        @Override
        public TicketResponseDto getTicketById(CustomUserPrincipal authUser, Long ticketId) {
                Ticket ticket = ticketRepository.findById(ticketId)
                                .orElseThrow(() -> new RuntimeException("Ticket not found"));
                return TicketResponseDto.fromEntity(ticket);

        }

        @Override
        public List<TicketResponseDto> getTicketsByReporterId(CustomUserPrincipal authUser, Long userId) {
                User user = userRepository.findById(userId)
                                .orElseThrow(() -> new RuntimeException("User not found"));

                List<Ticket> tickets = ticketRepository.findByReporter(user);
                return tickets.stream()
                                .map(TicketResponseDto::fromEntity)
                                .toList();

        }

        @Override
        public void addAttachment(CustomUserPrincipal authUser, Long ticketId, List<MultipartFile> files,
                        HttpServletRequest request) {
                Ticket ticket = ticketRepository.findById(ticketId)
                                .orElseThrow(() -> new RuntimeException("Ticket not found"));

                User currentUser = authUser.getUser();

                if (files != null && !files.isEmpty()) {
                        for (MultipartFile file : files) {
                                try {
                                        String filePath = fileStorageService.storeFile(file, "attachment");
                                        TicketAttachment attachment = new TicketAttachment();
                                        attachment.setTicket(ticket);
                                        attachment.setUploadedBy(currentUser);
                                        attachment.setFileName(file.getOriginalFilename());
                                        attachment.setFileUrl(filePath);
                                        attachment.setFileSizeKb((int) (file.getSize() / 1024));
                                        attachment.setMimeType(file.getContentType());
                                        TicketAttachment savedAttachment = attachmentRepository.save(attachment);

                                        auditLogService.logAction(
                                                        "TICKET_ATTACHMENT",
                                                        savedAttachment.getId(),
                                                        currentUser,
                                                        AuditAction.ATTACHMENT_UPLOADED,
                                                        Map.of(
                                                                        "ticketId", ticket.getId(),
                                                                        "ticketNo", ticket.getTicketNo(),
                                                                        "fileName", savedAttachment.getFileName(),
                                                                        "fileUrl", savedAttachment.getFileUrl(),
                                                                        "mimeType",
                                                                        savedAttachment.getMimeType() != null
                                                                                        ? savedAttachment.getMimeType()
                                                                                        : "",
                                                                        "fileSizeKb",
                                                                        savedAttachment.getFileSizeKb() != null
                                                                                        ? savedAttachment
                                                                                                        .getFileSizeKb()
                                                                                        : 0),
                                                        request);
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }

        }

        @Override
        public TicketResponseDto assignTicket(
                        CustomUserPrincipal authUser,
                        Long ticketId,
                        Long assigneeId,
                        HttpServletRequest request) {

                Ticket ticket = ticketRepository.findById(ticketId)
                                .orElseThrow(() -> new RuntimeException("No ticket found with id: " + ticketId));
                User assigneeUser = userRepository.findById(assigneeId)
                                .orElseThrow(() -> new RuntimeException("No user found with id: " + assigneeId));

                ticket.setAssignee(assigneeUser);

                ticket.setStatus(TicketStatus.IN_PROGRESS);
                ticket.setSlaDueAt(LocalDateTime.now().plusMinutes(ticket.getSlaPolicy().getResolutionMinutes()));
                ticket.setUpdatedAt(LocalDateTime.now());
                ticketRepository.save(ticket);
                auditLogService.logAction(
                                "TICKET",
                                ticket.getId(),
                                authUser.getUser(),
                                AuditAction.ASSIGNED,
                                Map.of(
                                                "ticketNo", ticket.getTicketNo(),
                                                "assigneeId", assigneeUser.getId(),
                                                "assignedTo", assigneeUser.getFullName(),
                                                "assignedBy", authUser.getUser().getFullName(),
                                                "subject", ticket.getSubject(),
                                                "priority", ticket.getPriority().toString(),
                                                "status", ticket.getStatus().toString()),
                                request);
                if (ticket.getAssignee() != null) {
                        notificationService.createNotification(
                                        ticket.getAssignee(),
                                        ticket,
                                        NotificationType.TICKET_ASSIGNED,
                                        "Ticket Assigned",
                                        "You have been assigned a new ticket",
                                        "/tickets/" + ticket.getId());
                }
                return TicketResponseDto.fromEntity(ticket);

        }

}
