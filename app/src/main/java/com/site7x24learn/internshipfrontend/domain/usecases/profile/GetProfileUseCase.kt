package com.site7x24learn.internshipfrontend.domain.usecases.profile



import com.site7x24learn.internshipfrontend.domain.models.user.UserProfile
import com.site7x24learn.internshipfrontend.domain.repositories.UserRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): UserProfile {
        return repository.getProfile().let { userDto ->
            UserProfile(
                id = userDto.id,
                name = userDto.name ?: "",
                email = userDto.email,
                gender = userDto.gender,
                birthDate = userDto.birthDate,
                phone = userDto.phone,
                address = userDto.address,
                role = null // Add role if needed
            )
        }
    }
}