package id.capstone.project.skindetector.ui.fragment.main.home

import androidx.lifecycle.ViewModel
import id.capstone.project.skindetector.data.model.DoctorEntity

class HomeViewModel : ViewModel() {
    fun getListDoctor(): List<DoctorEntity> =
        listOf(
            DoctorEntity(
                1,
                "https://s3-eu-west-1.amazonaws.com/intercare-web-public/wysiwyg-uploads%2F1569586526901-doctor.jpg",
                "dr. Adi Candra",
                "Skin Specialist",
                "Samarinda, Indonesia"
            ),
            DoctorEntity(
                2,
                "https://post.healthline.com/wp-content/uploads/2019/01/Male_Doctor_732x549-thumbnail.jpg",
                "dr. Indra Cahyanto",
                "Dermatology",
                "Balikpapan, Indonesia"
            ),

            DoctorEntity(
                3,
                "https://image.freepik.com/free-photo/front-view-doctor-with-medical-mask-posing-with-crossed-arms_23-2148445082.jpg",
                "dr. Henda Adi",
                "Urology",
                "Balikpapan, Indonesia"
            ),
            DoctorEntity(
                4,
                "https://familydoctor.org/wp-content/uploads/2018/02/41808433_l.jpg",
                "dr. Amrul Cahyanto",
                "Dentist",
                "Berau, Indonesia"
            ),
            DoctorEntity(
                1,
                "https://s3-eu-west-1.amazonaws.com/intercare-web-public/wysiwyg-uploads%2F1569586526901-doctor.jpg",
                "dr. Adi Candra",
                "Skin Specialist",
                "Samarinda, Indonesia"
            ),
            DoctorEntity(
                1,
                "https://s3-eu-west-1.amazonaws.com/intercare-web-public/wysiwyg-uploads%2F1569586526901-doctor.jpg",
                "dr. Adi Candra",
                "Skin Specialist",
                "Samarinda, Indonesia"
            ),
            DoctorEntity(
                1,
                "https://s3-eu-west-1.amazonaws.com/intercare-web-public/wysiwyg-uploads%2F1569586526901-doctor.jpg",
                "dr. Adi Candra",
                "Skin Specialist",
                "Samarinda, Indonesia"
            ),
            DoctorEntity(
                1,
                "https://s3-eu-west-1.amazonaws.com/intercare-web-public/wysiwyg-uploads%2F1569586526901-doctor.jpg",
                "dr. Adi Candra",
                "Skin Specialist",
                "Samarinda, Indonesia"
            ),
            DoctorEntity(
                1,
                "https://s3-eu-west-1.amazonaws.com/intercare-web-public/wysiwyg-uploads%2F1569586526901-doctor.jpg",
                "dr. Adi Candra",
                "Skin Specialist",
                "Samarinda, Indonesia"
            ),
            DoctorEntity(
                1,
                "https://s3-eu-west-1.amazonaws.com/intercare-web-public/wysiwyg-uploads%2F1569586526901-doctor.jpg",
                "dr. Adi Candra",
                "Skin Specialist",
                "Samarinda, Indonesia"
            )
        )
}