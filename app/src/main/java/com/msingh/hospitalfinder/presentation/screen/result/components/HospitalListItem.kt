package com.msingh.hospitalfinder.presentation.screen.result.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.Coil
import coil.compose.AsyncImage
import com.msingh.hospitalfinder.data.model.places.Geometry
import com.msingh.hospitalfinder.data.model.places.Location
import com.msingh.hospitalfinder.data.model.places.Northeast
import com.msingh.hospitalfinder.data.model.places.OpeningHours
import com.msingh.hospitalfinder.data.model.places.Photo
import com.msingh.hospitalfinder.data.model.places.PlusCode
import com.msingh.hospitalfinder.data.model.places.Result
import com.msingh.hospitalfinder.data.model.places.Southwest
import com.msingh.hospitalfinder.data.model.places.Viewport

@Composable
fun HospitalListItem(place: Result, distance: String?) {
    Log.d("ITEM", "HospitalListItem() called with: place = $place")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .background(
                shape = RoundedCornerShape(5.dp),
                color = Color.LightGray.copy(alpha = 0.3f)
            )
            .padding(10.dp)
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Top) {
                TitleAndData("Hospital Name", place.name)
                AsyncImage(
                    model = place.icon,
                    contentDescription = null,
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            TitleAndData("Address", place.vicinity)
            Spacer(modifier = Modifier.height(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleAndData(title = "Status", value = place.business_status)
                TitleAndData(title = "Distance", value = distance.toString())
                TitleAndData(title = "Rating", value = place.rating.toString())
            }
        }
    }
}

@Composable
fun TitleAndData(title: String, value: String) {
    Column {
        Text(text = title, fontSize = 12.sp, color = Color.Black.copy(alpha = 0.6f))
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = value, fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.Bold)
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview(showBackground = true)
fun PreviewHospitalListItem() {
    Scaffold {
        LazyColumn {
            items(3) {
                HospitalListItem(
                    place = Result(
                        business_status = "OPERATIONAL",
                        geometry = Geometry(
                            location = Location(lat = 32.5118365, lng = -87.8338943),
                            viewport = Viewport(
                                northeast = Northeast(
                                    lat = 32.5131809802915,
                                    lng = -87.8324030697085
                                ),
                                southwest = Southwest(
                                    lat = 32.5104830197085,
                                    lng = -87.83510103029151
                                )
                            )
                        ),
                        icon = "https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/generic_business-71.png",
                        icon_background_color = "#7B9EB0",
                        icon_mask_base_uri = "https://maps.gstatic.com/mapfiles/place_api/icons/v2/generic_pinlet",
                        name = "DaVita Demopolis Dialysis",
                        opening_hours = OpeningHours(open_now = false),
                        place_id = "ChIJt_Wt4SGYhYgRo9XBy7h6LGE",
                        plus_code = PlusCode(
                            compound_code = "G568+PC Demopolis, AL, USA",
                            global_code = "864JG568+PC"
                        ),
                        rating = 0.0,
                        reference = "ChIJt_Wt4SGYhYgRo9XBy7h6LGE",
                        scope = "GOOGLE",
                        types = listOf<String>(),
                        user_ratings_total = 0,
                        vicinity = "305 South Cedar Avenue, Demopolis",
                        photos = listOf<Photo>()
                    ),
                    distance = "3.6 km"
                )
            }
        }
    }
}