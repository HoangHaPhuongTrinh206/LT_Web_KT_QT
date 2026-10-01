package vn.hcmut.de03.model;

import java.util.List;

/** Kết quả phân trang dùng chung cho JSP (items, page, totalPages, startPage, endPage ...). */
public class PageResult_24162131<T> {
    private final List<T> items;
    private final int page;
    private final int pageSize;
    private final long totalItems;

    public PageResult_24162131(List<T> items, int page, int pageSize, long totalItems) {
        this.items = items;
        this.page = page;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
    }

    /** Ép số trang yêu cầu về khoảng [1, totalPages]. */
    public static int clampPage(int page, long totalItems, int pageSize) {
        int totalPages = (int) Math.max(1, Math.ceil(totalItems / (double) pageSize));
        return Math.min(Math.max(page, 1), totalPages);
    }

    public List<T> getItems() { return items; }
    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }
    public long getTotalItems() { return totalItems; }

    public int getTotalPages() {
        return (int) Math.max(1, Math.ceil(totalItems / (double) pageSize));
    }

    /** Hiển thị tối đa 5 số trang: << 1 2 3 4 5 >> */
    public int getStartPage() {
        return Math.max(1, Math.min(page - 2, getTotalPages() - 4));
    }

    public int getEndPage() {
        return Math.min(getTotalPages(), getStartPage() + 4);
    }
}
