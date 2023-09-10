package kz.job4j.cinema.model.request;

import java.util.Objects;

public class BuyRequest {
    private Integer filmSessionId;
    private Integer rowNumber;
    private Integer placeNumber;

    private Integer userId;

    public BuyRequest() {
    }

    public BuyRequest(Integer filmSessionId, Integer rowNumber, Integer placeNumber, Integer userId) {
        this.filmSessionId = filmSessionId;
        this.rowNumber = rowNumber;
        this.placeNumber = placeNumber;
        this.userId = userId;
    }

    public void setFilmSessionId(Integer filmSessionId) {
        this.filmSessionId = filmSessionId;
    }

    public Integer getFilmSessionId() {
        return filmSessionId;
    }

    public void setPlaceNumber(Integer placeNumber) {
        this.placeNumber = placeNumber;
    }

    public Integer getPlaceNumber() {
        return placeNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BuyRequest request = (BuyRequest) o;
        return userId == request.userId
                && filmSessionId == request.filmSessionId
                && placeNumber == request.placeNumber
                && rowNumber == request.rowNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, filmSessionId, placeNumber, rowNumber);
    }

    @Override
    public String toString() {
        return "[ {\n\tsessionId: " + filmSessionId + ",\n"
                + "\trowNumber: " + rowNumber + ",\n"
                + "\tplaceNumber: " + placeNumber + ",\n"
                + "\tuserId:" + userId + "\n} ]";
    }
}
