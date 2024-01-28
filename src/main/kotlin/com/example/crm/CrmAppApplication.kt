package com.example.crm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication()
class CrmAppApplication

fun main(args: Array<String>) {
	runApplication<CrmAppApplication>(*args)
}