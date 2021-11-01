package by.hardzeyeu.libraryV2.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

public class BookBorrowsInfo {
    int returned;
    int damaged;
    int lost;
    int borrowed;

    List<LocalDate> dueDatesWithoutStatus;

    public List<LocalDate> getDueDatesWithoutStatus() {
        return dueDatesWithoutStatus;
    }

    public void setDueDatesWithoutStatus(List<LocalDate> dueDatesWithoutStatus) {
        this.dueDatesWithoutStatus = dueDatesWithoutStatus;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    public int getDamaged() {
        return damaged;
    }

    public void setDamaged(int damaged) {
        this.damaged = damaged;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public int getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    @Override
    public String toString() {
        return "BookBorrowsInfo |returned=" + returned + "| damaged=" + damaged + "| lost=" + lost + "| borrowed=" + borrowed +
                " | dueDatesWithoutStatus " + dueDatesWithoutStatus.toString();
    }
}
