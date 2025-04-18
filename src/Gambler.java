import java.time.LocalDate;
import java.time.Period;

public class Gambler extends Player {
    private long bet = 0;
    private long earnings = 0;
    private double odds = 0.0;
    private LocalDate dob;

    public void setDateOfBirth(int[] birthday) {
        setDateOfBirth(birthday[0], birthday[1], birthday[2]);
    }
    public void setDateOfBirth(int day, int month, int year) {
        dob = LocalDate.of(day, month, year);
    }

    public String getDateOfBirth() {
        return dob.toString();
    }

    public boolean checkAge() {
        return Period.between(dob, LocalDate.now()).getYears() >= 18;
        /* Could write as below but more fun to use above:
        if (Period.between(dob, LocalDate.now()).getYears() >= 18 ) {
            return true;
        }
        return false;
        */
    }

    public double getBet() {
        return (double) bet / 100;
    }

    public void setBet(double bet) {
        this.bet = (long) bet * 100;
    }

    public double getEarnings() {
        return (double) earnings / 100;
    }

    public void setEarnings(Boolean won) {
        if (won) {
            this.earnings = (long) (earnings + odds);
        } else {
            this.earnings = (long) (earnings - odds);
        }
    }

    public void setOdds(int instances, int outcomes) {
        odds = (double) (instances / outcomes) * bet;
        // Technically this is just the percentage to get odds we'd need
        // odds = probability of event / probability event won't happen
        // So an event has an 11/9 odds if 11 can happen, 9 don't
        // That would require passing Maps of data though.
    }
}
