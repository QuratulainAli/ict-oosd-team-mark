package ca.sait.oosd.hibernate;
// Generated Aug 31, 2009 2:44:29 PM by Hibernate Tools 3.2.1.GA


import ca.sait.oosd.business.TEObject;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Fees generated by hbm2java
 */
public class Fees extends TEObject implements java.io.Serializable {


     private String feeid;
     private String feename;
     private BigDecimal feeamt;
     private String feedesc;
     private Set<Bookingdetails> bookingdetailses = new HashSet<Bookingdetails>(0);

    public Fees() {
    }

	
    public Fees(String feeid, String feename, BigDecimal feeamt) {
        this.feeid = feeid;
        this.feename = feename;
        this.feeamt = feeamt;
    }
    public Fees(String feeid, String feename, BigDecimal feeamt, String feedesc, Set<Bookingdetails> bookingdetailses) {
       this.feeid = feeid;
       this.feename = feename;
       this.feeamt = feeamt;
       this.feedesc = feedesc;
       this.bookingdetailses = bookingdetailses;
    }
   
    public String getFeeid() {
        return this.feeid;
    }
    
    public void setFeeid(String feeid) {
        this.feeid = feeid;
    }
    public String getFeename() {
        return this.feename;
    }
    
    public void setFeename(String feename) {
        this.feename = feename;
    }
    public BigDecimal getFeeamt() {
        return this.feeamt;
    }
    
    public void setFeeamt(BigDecimal feeamt) {
        this.feeamt = feeamt;
    }
    public String getFeedesc() {
        return this.feedesc;
    }
    
    public void setFeedesc(String feedesc) {
        this.feedesc = feedesc;
    }
    public Set<Bookingdetails> getBookingdetailses() {
        return this.bookingdetailses;
    }
    
    public void setBookingdetailses(Set<Bookingdetails> bookingdetailses) {
        this.bookingdetailses = bookingdetailses;
    }

    @Override
    public String toString() {
        return this.feename;
    }




}

