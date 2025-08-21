package com.abhi.classconnect.data.remote

import com.abhi.classconnect.data.dto.lessons.LessonDto
import com.abhi.classconnect.data.dto.students.StudentRecordDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import kotlin.random.Random

class FakeRemoteDataSource() {

    @GET
    suspend fun getLessons() = listOf<Nothing>()


    @POST("lessons")
    suspend fun uploadLessonToServer(@Body lesson: LessonDto) : Boolean {
        return Random.nextBoolean()
    }

    @POST("lessons")
    suspend fun uploadStudentRecordToServer(@Body record: StudentRecordDto) : Boolean {
        return Random.nextBoolean()
    }
}