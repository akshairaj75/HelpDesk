package com.backend.helpdeskpro.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import com.backend.helpdeskpro.enums.NotificationType;
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
        public TicketResponseDto createTicket(CustomUserPrincipal authUser, TicketCreateDto dto,
                        List<MultipartFile> files) {

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

                if (savedTicket.getAssignee() != null) {
                        notificationService.createNotification(
                                        savedTicket.getAssignee(),
                                        savedTicket,
                                        NotificationType.TICKET_ASSIGNED,
                                        "Ticket Assigned",
                                        "You have been assigned a new ticket",
                                        "/tickets/" + savedTicket.getId());
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
                }
                List<Ticket> tickets = ticketRepository.findAll();
                return tickets.stream()
                                .map(TicketResponseDto::fromEntity)
                                .toList();

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
        public void addAttachment(CustomUserPrincipal authUser, Long ticketId, List<MultipartFile> files) {
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
                                        attachmentRepository.save(attachment);
                                } catch (IOException e) {
                                        e.printStackTrace();
                                }
                        }
                }

        }

}
