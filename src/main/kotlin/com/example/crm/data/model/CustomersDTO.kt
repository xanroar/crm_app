package com.example.crm.data.model

import com.example.crm.data.model.Customers


/// В рамках этого проекта оставил CustomersDTO в общей папке model
/// В крупных проектах, насколько понимаю, для удобства лучше вынести в отдельную папку dto
data class CustomersDTO(
    val id: Int? = null,
    val name: String,
    val email: String,
    val phone: String
){
    /// По логике toDTO и toEntity нужно в отдельный класс выносить
    /// Сделал это с помощью создания доп сервиса
    /// (Ещё вариант Util class с аннотацией @Component использовать)
    /// По итогу все вернул обратно к варианту с созданием методов прямо в CustomersDTO и Customers
    /// Почему-то не хочется заграмождать контроллер
    fun toEntity(): Customers {
        return Customers(id = id, name = name, email = email, phone = phone)
    }
}