package me.tatocaster.nasaappchallenge.data

/*

@Module
abstract class DataModule {

    @Binds
    fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Binds
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            Timber.tag("Let_OkHttp").d(message)
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return Retrofit.Builder()
                .baseUrl("")
                .client(OkHttpClient.Builder()
                        .addInterceptor(loggingInterceptor)
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

}*/
