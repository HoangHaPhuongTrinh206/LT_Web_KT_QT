package vn.hcmut.de03.entity;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "Favorite")
@Table(name = "Favorites")
public class Favorite_24162131 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FavoriteId")
    private Integer favoriteId;

    @Column(name = "LikedDate")
    private LocalDate likedDate;

    @ManyToOne
    @JoinColumn(name = "VideoId")
    private Video_24162131 video;

    @ManyToOne
    @JoinColumn(name = "Username")
    private User_24162131 user;

    public Favorite_24162131() {
    }

    public Integer getFavoriteId() { return favoriteId; }
    public void setFavoriteId(Integer favoriteId) { this.favoriteId = favoriteId; }
    public LocalDate getLikedDate() { return likedDate; }
    public void setLikedDate(LocalDate likedDate) { this.likedDate = likedDate; }
    public Video_24162131 getVideo() { return video; }
    public void setVideo(Video_24162131 video) { this.video = video; }
    public User_24162131 getUser() { return user; }
    public void setUser(User_24162131 user) { this.user = user; }
}
