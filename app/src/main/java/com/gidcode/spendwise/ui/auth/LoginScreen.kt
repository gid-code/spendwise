package com.gidcode.spendwise.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gidcode.spendwise.R
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator

@Composable
fun LoginScreen() {
   LoginScreenContent()
}


@Composable
fun LoginScreenContent(){
   val navController = Navigator.current
   var email by remember { mutableStateOf("") }
   var password by remember { mutableStateOf("") }
   var passwordVisible by remember { mutableStateOf(false) }

   val isFormValid by remember { derivedStateOf {
      email.isNotBlank() && password.isNotBlank()
   } }

   Surface(
      modifier = Modifier.fillMaxSize()
   ) {
      Column(
         modifier = Modifier.padding(
            vertical = 10.dp,
            horizontal = 16.dp
         ),
         verticalArrangement = Arrangement.Top
      ) {
         Spacer(modifier = Modifier.weight(1f))
         Text(
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.displayMedium.copy(
               fontSize = 36.sp,
               fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Start
         )

         Text(
            text = "Enter your email and password to log in",
            style = MaterialTheme.typography.bodyMedium.copy(
               fontSize = 12.sp,
               fontWeight = FontWeight.Medium,
               color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            textAlign = TextAlign.Start
         )

         Spacer(modifier = Modifier.height(8.dp))

         OutlinedTextField(
            value = email,
            onValueChange = {email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
         )

         Spacer(modifier = Modifier.height(8.dp))

         OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
               val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
               IconButton(onClick = { passwordVisible = !passwordVisible }) {
                  Icon(imageVector = icon, contentDescription = "Toggle password visibility")
               }
            },
            modifier = Modifier.fillMaxWidth()
         )

         Spacer(modifier = Modifier.height(16.dp))

         // Login Button
         Button(
            onClick = {
               navController.navigate(Destination.Home.route){
                  popUpTo(Destination.Authentication.route) { inclusive = true }
               }
            },
            modifier = Modifier.fillMaxWidth()
         ) {
            Text("Login",
               style = MaterialTheme.typography.bodyMedium
            )
         }

         Spacer(modifier = Modifier.weight(1f))

         Text(
            modifier = Modifier
               .align(Alignment.CenterHorizontally)
               .clickable {
                  navController.navigate(Destination.SignUp.route)
               }
            ,
            text = buildAnnotatedString {
               withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)){
                  append("Don't have an account? ")
               }
               withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
                  append("Sign up")
               }
            },
            style = MaterialTheme.typography.bodyMedium.copy(
               fontSize = 14.sp,
               fontWeight = FontWeight.Medium
            ),
            textAlign = TextAlign.Center
         )

         Spacer(modifier = Modifier.height(25.dp))

      }
   }
}

@Preview(name = "Login")
@Composable
fun LoginScreenPreview() {
   PreviewContent {
      LoginScreenContent()
   }
}

@Preview(name = "Login (Dark)")
@Composable
fun LoginScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      LoginScreenContent()
   }
}