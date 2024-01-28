package com.example.crm.data.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "customer_table")
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customers_seq")
    @SequenceGenerator(name = "customers_seq", sequenceName = "customers_seq", allocationSize = 1)
    var id: Long? = null

    @Column(name = "firstname")
    var firstName: String? = null

    @Column(name = "lastname")
    var lastName: String? = null

    @Column(name = "emailaddress")
    var emailAddress: String? = null

    @Column(name = "address")
    var address: String? = null

    @Column(name = "city")
    var city: String? = null

    @Column(name = "phonenumber")
    var phoneNumber: String? = null
}
