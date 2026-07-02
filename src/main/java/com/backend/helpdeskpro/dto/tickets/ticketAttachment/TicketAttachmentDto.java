package com.backend.helpdeskpro.dto.tickets.ticketAttachment;

import java.util.List;

import com.backend.helpdeskpro.entity.TicketAttachment;

public class TicketAttachmentDto {
    private Long attachmentId;
    private String fileName;
    private String fileUrl;
    private String mimeType;
    private Integer fileSizeKb;

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Integer getFileSizeKb() {
        return fileSizeKb;
    }

    public void setFileSizeKb(Integer fileSizeKb) {
        this.fileSizeKb = fileSizeKb;
    }

    public static TicketAttachmentDto fromEntity(TicketAttachment attachment) {
        TicketAttachmentDto dto = new TicketAttachmentDto();
        dto.setAttachmentId(attachment.getId());
        dto.setFileName(attachment.getFileName());
        dto.setFileUrl(attachment.getFileUrl());
        dto.setMimeType(attachment.getMimeType());
        dto.setFileSizeKb(attachment.getFileSizeKb());
        return dto;
    }

}
