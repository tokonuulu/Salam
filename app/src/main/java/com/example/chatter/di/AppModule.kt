package com.example.chatter.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.chatter.repository.AuthRepository
import com.example.chatter.repository.DatabaseRepository
import com.example.chatter.repository.FirebaseAuthSource
import com.example.chatter.repository.FirebaseDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideStorageReference() : StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    @Singleton
    @Provides
    fun provideFirebaseAuthInstance(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirestoreInstance(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthSource(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): FirebaseAuthSource {
        return FirebaseAuthSource(firebaseAuth, firebaseFirestore)
    }

    @Singleton
    @Provides
    fun provideAuthRepository (firebaseAuthSource: FirebaseAuthSource) : AuthRepository {
        return AuthRepository(firebaseAuthSource)
    }

    @Singleton
    @Provides
    fun provideDataSource(
        firebaseAuthSource: FirebaseAuthSource,
        firebaseFirestore: FirebaseFirestore,
        storageReference: StorageReference
    ) : FirebaseDataSource {
        return FirebaseDataSource(firebaseAuthSource, firebaseFirestore, storageReference)
    }

    @Singleton
    @Provides
    fun provideDatabaseRepository(firebaseDataSource: FirebaseDataSource) : DatabaseRepository {
        return DatabaseRepository(firebaseDataSource)
    }

    @Singleton
    @Provides
    fun provideGlideInstance(application: Application) : RequestManager {
        return Glide.with(application)
    }

}