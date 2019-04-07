package fr.slickteam.back

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse(val success: Boolean = true, val msg: String? = null, val content: Any? = null)