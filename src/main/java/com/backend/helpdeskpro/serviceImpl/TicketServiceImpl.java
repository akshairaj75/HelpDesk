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
import com.backend.helpdeskpro.repository.CategoryRepository;
import com.backend.helpdeskpro.repository.DepartmentRepository;
import com.backend.helpdeskpro.repository.SlaPolicyRepository;
import com.backend.helpdeskpro.repository.TicketAttachmentRepository;
import com.backend.helpdeskpro.repository.TicketRepository;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
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
                List<Ticket> tickets = ticketRepository.findAll();
                return tickets.stream()
                                .map(TicketResponseDto::fromEntity)
                                .toList();

        }

}
