package vn.hcmut.de03.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Video")
@Table(name = "Videos")
public class Video_24162131 implements Serializable {
    private static final long serialVersionUID = 1L;

    // VideoId là nvarchar(50) nên không thể IDENTITY -> Service tự sinh mã "VID" + timestamp
    @Id
    @Column(name = "VideoId", length = 50)
    private String videoId;

    @Column(name = "Title", length = 200)
    private String title;

    @Column(name = "Poster", length = 50)
    private String poster;

    @Column(name = "Views")
    private Integer views;

    @Column(name = "Description", length = 500)
    private String description;

    @Column(name = "Active")
    private Boolean active;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Category_24162131 category;

    // Xóa video thì xóa luôn Shares / Favorites liên quan (tránh lỗi khóa ngoại)
    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    private List<Share_24162131> shares;

    @OneToMany(mappedBy = "video", cascade = CascadeType.REMOVE)
    private List<Favorite_24162131> favorites;

    // Số lượng Share / Like (không lưu DB, Service tính bằng query count)
    @Transient
    private long shareCount;

    @Transient
    private long likeCount;

    public Video_24162131() {
    }

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public Integer getViews() { return views; }
    public void setViews(Integer views) { this.views = views; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Category_24162131 getCategory() { return category; }
    public void setCategory(Category_24162131 category) { this.category = category; }
    public List<Share_24162131> getShares() { return shares; }
    public void setShares(List<Share_24162131> shares) { this.shares = shares; }
    public List<Favorite_24162131> getFavorites() { return favorites; }
    public void setFavorites(List<Favorite_24162131> favorites) { this.favorites = favorites; }
    public long getShareCount() { return shareCount; }
    public void setShareCount(long shareCount) { this.shareCount = shareCount; }
    public long getLikeCount() { return likeCount; }
    public void setLikeCount(long likeCount) { this.likeCount = likeCount; }
}
