package com.gidcode.spendwise.ui.auth

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gidcode.spendwise.R
import com.gidcode.spendwise.ui.common.AnimatedImage
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator
import com.gidcode.spendwise.ui.theme.primaryLight

@Composable
fun OnboardingScreen() {
   var visible by remember { mutableStateOf(false) }
   LaunchedEffect(true) {
      visible = true
   }

   OnboardingScreenContent(visible)
}

@Composable
fun OnboardingScreenContent(
   visible: Boolean
) {
   val navController = Navigator.current
   Surface(
      color = Color.White
   ) {
      Column (
         modifier = Modifier.fillMaxSize()
      ){
         Box(
            modifier = Modifier
               .fillMaxWidth()
               .background(
                  color = Color(0xFFEEF8F7)
               )
               .fillMaxHeight(0.65f),
            contentAlignment = Alignment.BottomCenter
         ){
            val imageEnterTransition = fadeIn(
               animationSpec = tween(2000)
            )
            AnimatedImage(
               visible,
               imageEnterTransition,
               R.drawable.guy,
               "Guy",
               Modifier
                  .fillMaxHeight(0.8f)
                  .fillMaxWidth()
            )
         }

         Column(
            modifier = Modifier
               .padding(
                  horizontal = 18.dp,
                  vertical = 10.dp
               )
         ){
            Text(
               text = stringResource(id = R.string.onboarding_msg),
               style = MaterialTheme.typography.displayMedium.copy(
                  color = primaryLight,
                  fontSize = 36.sp,
                  fontWeight = FontWeight.Bold
               ),
               textAlign = TextAlign.Center
            )

            Box(
               modifier = Modifier
                  .background(
                     brush = Brush.linearGradient(
                        colors = listOf(
                           Color(0xFF69AEA9),
                           Color(0xFF3F8782)
                        )
                     ),
                     shape = RoundedCornerShape(size = 30.dp)
                  )
                  .height(60.dp)
                  .fillMaxWidth()
                  .clickable {
                     navController.navigate(Destination.SignUp.route){
                        popUpTo(Destination.OnBoarding.route) { inclusive = true }
                     }
                  }
            ){
               Text(
                  modifier = Modifier.align(Alignment.Center),
                  text = "Get Started",
                  style = MaterialTheme.typography.bodyMedium.copy(
                     color = Color.White,
                     fontSize = 18.sp,
                     fontWeight = FontWeight.SemiBold
                  ),
                  textAlign = TextAlign.Center
               )
            }
            Spacer(modifier = Modifier.height(25.dp))

            Text(
               modifier = Modifier.align(Alignment.CenterHorizontally)
                  .clickable {
                     navController.navigate(Destination.Login.route){
                        popUpTo(Destination.OnBoarding.route) { inclusive = true }
                     }
                  }
               ,
               text = buildAnnotatedString {
                  withStyle(style = SpanStyle(color = Color(0xFF444444))){
                     append("Already have an account? ")
                  }
                  withStyle(style = SpanStyle(color = primaryLight)){
                     append("Log in")
                  }
               },
               style = MaterialTheme.typography.bodyMedium.copy(
                  fontSize = 16.sp,
                  fontWeight = FontWeight.SemiBold
               ),
               textAlign = TextAlign.Center
            )
         }

      }
   }
}

@Preview(name = "Onboarding")
@Composable
fun OnboardingScreenPreview() {
   PreviewContent {
      OnboardingScreenContent(true)
   }
}

@Preview(name = "Onboarding (Dark)")
@Composable
fun OnboardingScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      OnboardingScreenContent(true)
   }
}