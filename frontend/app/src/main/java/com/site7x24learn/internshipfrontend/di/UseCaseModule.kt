package com.site7x24learn.internshipfrontend.di

import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.domain.repositories.AuthRepository
import com.site7x24learn.internshipfrontend.domain.repositories.InternshipRepository
import com.site7x24learn.internshipfrontend.domain.repositories.StudentStatusRepository
import com.site7x24learn.internshipfrontend.domain.repositories.UserRepository
import com.site7x24learn.internshipfrontend.domain.usecases.application.CheckExistingApplicationUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.CreateApplicationUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.DeleteApplicationUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.GetApplicationByIdUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.GetUserApplicationsUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.UpdateApplicationStatusUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.application.UpdateApplicationUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.auth.LoginUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.auth.SignUpUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.internships.CreateInternshipUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.internships.DeleteInternshipUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.internships.GetCategoriesUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.internships.GetInternshipByIdUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.internships.GetInternshipsUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.internships.UpdateInternshipUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.profile.DeleteProfileUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.profile.GetProfileUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.profile.UpdateProfileUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.student.UpdateStudentStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    // Auth Use Cases
    @Provides
    fun provideLoginUseCase(repo: AuthRepository) = LoginUseCase(repo)

    @Provides
    fun provideSignUpUseCase(repo: AuthRepository) = SignUpUseCase(repo)

    // Internship Use Cases
    @Provides
    fun provideGetCategoriesUseCase(repository: InternshipRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

    @Provides
    fun provideCreateInternshipUseCase(repository: InternshipRepository): CreateInternshipUseCase {
        return CreateInternshipUseCase(repository)
    }

    @Provides
    fun provideGetInternshipsUseCase(repository: InternshipRepository): GetInternshipsUseCase {
        return GetInternshipsUseCase(repository)
    }

    @Provides
    fun provideGetInternshipByIdUseCase(repository: InternshipRepository): GetInternshipByIdUseCase {
        return GetInternshipByIdUseCase(repository)
    }

    @Provides
    fun provideUpdateInternshipUseCase(repository: InternshipRepository): UpdateInternshipUseCase {
        return UpdateInternshipUseCase(repository)
    }

    @Provides
    fun provideDeleteInternshipUseCase(repository: InternshipRepository): DeleteInternshipUseCase {
        return DeleteInternshipUseCase(repository)
    }

    // Application Use Cases
    @Provides
    fun provideCreateApplicationUseCase(repository: ApplicationRepository) = CreateApplicationUseCase(repository)

    @Provides
    fun provideGetUserApplicationsUseCase(repository: ApplicationRepository) = GetUserApplicationsUseCase(repository)

    @Provides
    fun provideGetApplicationByIdUseCase(repository: ApplicationRepository) = GetApplicationByIdUseCase(repository)

    @Provides
    fun provideUpdateApplicationUseCase(repository: ApplicationRepository) = UpdateApplicationUseCase(repository)

    @Provides
    fun provideDeleteApplicationUseCase(repository: ApplicationRepository) = DeleteApplicationUseCase(repository)

    @Provides
    fun provideCheckExistingApplicationUseCase(repository: ApplicationRepository) = CheckExistingApplicationUseCase(repository)

    @Provides
    fun provideUpdateApplicationStatusUseCase(repository: ApplicationRepository) = UpdateApplicationStatusUseCase(repository)

    // Student Use Cases
    @Provides
    fun provideUpdateStudentStatusUseCase(repository: StudentStatusRepository): UpdateStudentStatusUseCase {
        return UpdateStudentStatusUseCase(repository)
    }
    @Provides
    fun provideGetProfileUseCase(repository: UserRepository): GetProfileUseCase {
        return GetProfileUseCase(repository)
    }

    @Provides
    fun provideUpdateProfileUseCase(repository: UserRepository): UpdateProfileUseCase {
        return UpdateProfileUseCase(repository)
    }

    @Provides
    fun provideDeleteProfileUseCase(repository: UserRepository): DeleteProfileUseCase {
        return DeleteProfileUseCase(repository)
    }
}