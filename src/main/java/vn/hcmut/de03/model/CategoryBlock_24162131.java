package vn.hcmut.de03.model;

import vn.hcmut.de03.entity.Category_24162131;
import vn.hcmut.de03.entity.Video_24162131;

/** Một khối "Category + danh sách video phân trang" trên trang Home (Câu 4, Câu 5). */
public class CategoryBlock_24162131 {
    private Category_24162131 category;
    private long total;                                  // Câu 5: số video của category
    private PageResult_24162131<Video_24162131> page;    // video của trang hiện tại (3 video/trang)
    private String otherParams = "";                     // tham số trang của các category khác, vd "p1=2&p3=1&"

    public Category_24162131 getCategory() { return category; }
    public void setCategory(Category_24162131 category) { this.category = category; }
    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public PageResult_24162131<Video_24162131> getPage() { return page; }
    public void setPage(PageResult_24162131<Video_24162131> page) { this.page = page; }
    public String getOtherParams() { return otherParams; }
    public void setOtherParams(String otherParams) { this.otherParams = otherParams; }
}
