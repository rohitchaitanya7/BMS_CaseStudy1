package com.bms.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bms.validators.AccountType;
import com.bms.validators.GenderType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
@Table(name = "Customer_details")
public class Customer {
	
	@JsonIgnore
	@Id
	private String customer_id;
	
	@NotBlank(message = "Customer name is mandatory ")
	@Pattern(regexp = "[a-zA-Z ]*$", message = "Customer name should contain only alphabets and space")
	@Column
	private String name;
	
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loginDetails_id", referencedColumnName = "username")
	private CustomerData loginDetails;
	
	@NotBlank(message = "Guardian Type is mandatory ")
	@Column
	private String guardianType;
	@NotBlank(message = "Guardian Name is mandatory ")
	@Pattern(regexp = "[a-zA-Z ]*$", message = "Guardian Name should contain only alphabets and space")
	@Column
	private String guardianName;
	@NotBlank(message = "Address is mandatory ")
	@Column
	private String address;
	@NotBlank(message = "CitizenShip is mandatory ")
	@Column
	private String citizenship;
	@NotBlank(message = "State Name is mandatory ")
	@Column
	private String state;
	@NotBlank(message = "Country Name is mandatory ")
	@Column
	private String country;
	@NotBlank(message = "Email is mandatory ")
	@Email
	@Column
	private String emailAddress;
	@GenderType
	@NotBlank(message = "gender Name is mandatory ")
	@Column
	private String gender;
	@NotBlank(message = "maritalStatus Name is mandatory ")
	@Column
	private String maritalStatus;
	@Pattern(regexp = "[6-9]{1}[0-9]{9}", message = "Contact Number should contain only 10 digitis  and should start with 6,7,8,9")
	@Column
	private String contactNo;
	@NotNull(message = "Date Of Birth  is mandatory ")
	@Past
	@Column
	private Date dob;
	@JsonIgnore
	@Column
	private Date registrationDate;
	@JsonIgnore
	@Column
	private String citizenStatus;
	@NotBlank(message = "Identification Proof Type  is mandatory ")
	@Column
	private String identificationProofType;
	@NotBlank(message = "Identification Document Number  is mandatory ")
	@Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-z]{1}", message = "Enter valid Pan Card Number ")
	@Column
	private String identificationNumber;
	@NotBlank(message = "Reference Account Holder Name mandatory ")
	@Pattern(regexp = "[a-zA-Z ]*$", message = "Account Holder Name should contain only alphabets and space")
	@Column
	private String ReferenceAccountHolderName;
	@NotBlank(message = "Reference Account Holder Account Number  is mandatory ")
	@Column
	private String ReferenceAccountHolderAccountNo;
	@NotBlank(message = "reference Account Holder Address  is mandatory ")
	@Column
	private String ReferenceAccountHolderAddress;
	@AccountType
	@NotBlank(message = "Account Type  is mandatory ")
	@Column
	private String accountType;
	@NotBlank(message = "Bank Name  is mandatory ")
	@Size(min = 3 , message = "Bank Name Atleast 3 Characters long")
	@Column
	private String bankName;
	@NotBlank(message = "Branch Name  is mandatory ")
	@Size(min = 3 , message = "Branch Name Atleast 3 Characters long")
	@Column
	private String branchName;
	@NotNull(message = "Initial Deposit Amount  is mandatory ")
	@Column 
	private Long depositAmount;

}
