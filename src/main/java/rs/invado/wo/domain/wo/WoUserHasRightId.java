package rs.invado.wo.domain.wo;

// Generated Mar 6, 2013 10:35:50 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * WoUserHasRightId generated by hbm2java
 */
@Embeddable
public class WoUserHasRightId implements java.io.Serializable {

	private Integer userId;
	private Integer rigthId;


	public WoUserHasRightId() {
	}

	public WoUserHasRightId(Integer userId, BigDecimal rigthId) {
		this.userId = userId;
	}

	@Column(name = "USER_ID", precision = 22, scale = 0)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "RIGTH_ID", precision = 22, scale = 0)
	public Integer getRigthId() {
		return this.rigthId;
	}

	public void setRigthId(Integer rigthId) {
		this.rigthId = rigthId;
	}



	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof WoUserHasRightId))
			return false;
		WoUserHasRightId castOther = (WoUserHasRightId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getRigthId() == castOther.getRigthId()) || (this
						.getRigthId() != null && castOther.getRigthId() != null && this
						.getRigthId().equals(castOther.getRigthId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getRigthId() == null ? 0 : this.getRigthId().hashCode());
		return result;
	}

}
