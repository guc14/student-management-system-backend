package com.guc.studentmanagement.dto;

import java.util.List;

public class PageResponse<T> {

    private List<T> content;     // Data of the current page
    private int page;            // Current page index (0-based)
    private int size;            // Number of items per page
    private long totalElements;  // Total number of elements
    private int totalPages;      // Total number of pages
    private boolean last;        // Indicates whether this is the last page

    public PageResponse() {
    }

    public PageResponse(List<T> content,
                        int page,
                        int size,
                        long totalElements,
                        int totalPages,
                        boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = last;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
