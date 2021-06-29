package com.bms.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Positive;

import com.bms.validation.LoanType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table
public class Loan{
	@Id
	private String loan_no;
	@Column
	@JsonIgnore
	private String cid;
	@Column
	@LoanType
	private String Type;
	@Positive
	@Column
	private long Amount;
	@Column
	@JsonIgnore
	private Date ApplyDate;
	@Column
	@JsonIgnore
	private Date IssueDate;
	@Column
	@JsonIgnore
	private double rateOfInterest;
	@Column
	private int durationInYears;
	@Column
	@JsonIgnore
	private String status;
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loan_detail_id", referencedColumnName = "id")
	private LoanDetails loanDetails;
	
	
}
