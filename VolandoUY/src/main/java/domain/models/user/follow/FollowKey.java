package domain.models.user.follow;

import domain.models.user.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class FollowKey implements Serializable {
    @ManyToOne
    private User follower;

    @ManyToOne
    private User followed;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowKey that = (FollowKey) o;

        if (!follower.getNickname().equals(that.follower.getNickname())) return false;
        return followed.getNickname().equals(that.followed.getNickname());
    }

    public int hashCode() {
        int result = follower.getNickname().hashCode();
        result = 31 * result + followed.getNickname().hashCode();
        return result;
    }
}
