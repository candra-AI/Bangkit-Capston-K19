package id.capstone.project.skindetector.di

import id.capstone.project.skindetector.domain.usecase.SkinInteractor
import id.capstone.project.skindetector.domain.usecase.SkinUseCase
import id.capstone.project.skindetector.ui.fragment.main.camera.CameraViewModel
import id.capstone.project.skindetector.ui.fragment.main.home.HomeViewModel
import id.capstone.project.skindetector.ui.fragment.other.detectionresult.DetectionResultViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<SkinUseCase> { SkinInteractor(get()) }
}
val viewModelModule = module {
    viewModel { HomeViewModel() }
    viewModel { CameraViewModel(get()) }
    viewModel { DetectionResultViewModel(get()) }
}