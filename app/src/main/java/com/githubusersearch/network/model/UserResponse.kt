package com.githubusersearch.network.model

/**
 * Created by ankit on 30/01/18.
 */
data class UserResponse(var total_count : String? = null,
                        var incomplete_results : Boolean?,
                        var items : List<User>?)