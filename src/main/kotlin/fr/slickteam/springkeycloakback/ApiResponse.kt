package fr.slickteam.springkeycloakback

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse(val success: Boolean = true, val msg: String? = null, val content: Any? = null)