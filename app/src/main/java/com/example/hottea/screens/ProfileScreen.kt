package com.example.hottea.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.hottea.R
import com.example.hottea.ViewModels.UserViewModel
import com.example.hottea.composables.Input
import com.example.hottea.composables.PrimaryButton
import com.example.hottea.models.User
import com.example.hottea.repositories.AuthRepository
import com.example.hottea.repositories.FirestoreRepository
import com.example.hottea.ui.theme.Blue
import com.example.hottea.ui.theme.HotTeaTheme
import com.example.hottea.ui.theme.Primary
import com.example.hottea.ui.theme.Yellow

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, repository: FirestoreRepository = FirestoreRepository(), authRepository: AuthRepository = AuthRepository(), viewModel: UserViewModel = viewModel(), navBack: () -> Unit){
    var mode by remember { mutableStateOf("dark") }
    val user by remember(viewModel.profile) {
        derivedStateOf { viewModel.profile }
    }

    var usernameInput by remember { mutableStateOf("") }
    var statusInput by remember { mutableStateOf("") }
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    Log.d("FUCK SAKES", user?.id.toString() ?: "Image URI is null")

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    var status = user?.status.toString()

    Log.i("USER", status)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(310.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {

                    AsyncImage(
                        model = if (selectedImageUri == null) user?.profileImage else selectedImageUri ,

                        modifier = Modifier
                            .fillMaxSize()
                            .blur(radius = 10.dp)
                        ,
                        colorFilter = ColorFilter.tint(Color.Gray, blendMode = BlendMode.Multiply),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .background(Blue, shape = CircleShape)
                                .size(170.dp)
                                .padding(5.dp)
                                .clickable {}
                        ) {
                            AsyncImage(
                                model = if (selectedImageUri == null) user?.profileImage else selectedImageUri ,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                                    .clickable {
                                        singlePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }
                                ,
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.size(12.dp))
//
                        Text(text = user?.username.toString(), color = Color.White)
                        Spacer(modifier = Modifier.size(7.dp))

                        Text(
                            text = if(user?.status == null)  "No status provided" else user?.status.toString(),
                            color = Color.White
                        )
                    }
                }
            }
        }
        TextButton(onClick = { navBack.invoke() }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            Text(text = "Back")
        }
        Column(
            modifier
                .fillMaxSize()
                .padding(15.dp)) {


            Text(text = "General", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight(700), )


            Column(
                modifier
                    .fillMaxSize()
                    .padding(0.dp, 12.dp), verticalArrangement = Arrangement.SpaceBetween) {
                PrimaryButton(
                    color = if (mode == "light") Blue else Yellow,
                    icon = if (mode == "light") ImageVector.vectorResource(id = R.drawable.ic_dark )  else ImageVector.vectorResource(id = R.drawable.ic_sunny ),
                    text = if (mode == "light") "Switch to dark mode" else "Switch to light mode"
                ) {
                    if(mode == "light"){
                        mode = "dark"
                    } else{
                        mode = "light"
                    }
                }

                Input(
                    textValue = usernameInput,
                    changedVal = {newText -> usernameInput = newText} ,
                    label = "Username" ,
                    icon = Icons.Default.Person,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    transformation = VisualTransformation.None
                )

                Input(
                    textValue = statusInput,
                    changedVal = {newText -> statusInput = newText} ,
                    label = "Status" ,
                    icon = ImageVector.vectorResource(
                        id = R.drawable.ic_edit),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    transformation = VisualTransformation.None
                )

                Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    PrimaryButton(color = Blue, icon = ImageVector.vectorResource(
                        id = R.drawable.ic_add
                    ), text = "Update my profile details" ) {
                       if(usernameInput == ""){
                           repository.updateUserData(user?.id.toString(), user?.username.toString() , statusInput, selectedImageUri.toString())
                       }else if(statusInput == ""){
                               repository.updateUserData(user?.id.toString(), usernameInput, user?.status.toString(), selectedImageUri.toString())
                       }
                    }
                }

                Text(text = "Danger Zone", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight(700))

                Column(modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    PrimaryButton(color = Color.Red, icon = ImageVector.vectorResource(
                        id = R.drawable.ic_remove
                    ), text = "Delete my account" ) {
                        Log.i("Adding friend", "YEs sir")
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun previewProfileScreen(){
    HotTeaTheme {
        ProfileScreen(navBack = {})
    }
}