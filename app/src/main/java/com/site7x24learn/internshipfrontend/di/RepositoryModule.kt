package com.site7x24learn.internshipfrontend.di

import com.site7x24learn.internshipfrontend.data.datasources.local.PreferencesManager
import com.site7x24learn.internshipfrontend.data.datasources.remote.ApiService
import com.site7x24learn.internshipfrontend.data.datasources.remote.UserRemoteDataSource
import com.site7x24learn.internshipfrontend.data.repositories.ApplicationRepositoryImpl
import com.site7x24learn.internshipfrontend.data.repositories.AuthRepositoryImpl
import com.site7x24learn.internshipfrontend.data.repositories.InternshipRepositoryImpl
import com.site7x24learn.internshipfrontend.data.repositories.StudentStatusRepositoryImpl
import com.site7x24learn.internshipfrontend.data.repositories.UserRepositoryImpl
import com.site7x24learn.internshipfrontend.domain.repositories.ApplicationRepository
import com.site7x24learn.internshipfrontend.domain.repositories.AuthRepository
import com.site7x24learn.internshipfrontend.domain.repositories.InternshipRepository
import com.site7x24learn.internshipfrontend.domain.repositories.StudentStatusRepository
import com.site7x24learn.internshipfrontend.domain.repositories.UserRepository
import com.site7x24learn.internshipfrontend.domain.usecases.profile.DeleteProfileUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.profile.GetProfileUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.profile.UpdateProfileUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.student.GetStudentsUseCase
import com.site7x24learn.internshipfrontend.domain.usecases.student.UpdateStudentStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    // Auth Repository
    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        preferencesManager: PreferencesManager
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, preferencesManager)
    }

    // Internship Repository
    @Provides
    @Singleton
    fun provideInternshipRepository(
        apiService: ApiService
    ): InternshipRepository {
        return InternshipRepositoryImpl(apiService)
    }

    // Application Repository
    @Provides
    @Singleton
    fun provideApplicationRepository(
        apiService: ApiService
    ): ApplicationRepository {
        return ApplicationRepositoryImpl(apiService)
    }

    // Student Status Repository
    @Provides
    @Singleton
    fun provideStudentStatusRepository(api: ApiService): StudentStatusRepository {
        return StudentStatusRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetStudentsUseCase(repository: StudentStatusRepository): GetStudentsUseCase {
        return GetStudentsUseCase(repository)
    }
    @Provides
    @Singleton
    fun provideUserRepository(remoteDataSource: UserRemoteDataSource): UserRepository {
        return UserRepositoryImpl(remoteDataSource)
    }


}