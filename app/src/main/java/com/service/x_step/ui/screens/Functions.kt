package com.service.x_step.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun underlinedTextField(
    value : String,
    onValueChange : (String) -> Unit,
    label : String,
    icon : ImageVector,
    description: String,

    ){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        trailingIcon = {
            Icon(imageVector = icon,
                contentDescription =  description
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.DarkGray,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.LightGray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun enterTextField(
    value : String,
    onValueChange: (String) -> Unit,
    label: String,
    field : String,
    onSuccess : (String) -> Unit
){
    var userId = FirebaseAuth.getInstance().currentUser?.uid

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        trailingIcon = {
            Icon(imageVector = Icons.Default.ChevronRight,
                contentDescription = "Submit",
                tint = Color.White,
                modifier = Modifier.clickable{
                    if (userId != null) {
                        FirebaseFirestore.getInstance().collection("user").document(userId)
                            .update(field, value)
                            .addOnSuccessListener {
                                onSuccess(value)
                            }
                    }

                }
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.White,
            focusedLabelColor = Color.White,
            unfocusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.White,
            unfocusedLabelColor = Color.White,
            disabledContainerColor = Color.Transparent,
            disabledIndicatorColor = Color.LightGray,
            disabledLabelColor = Color.LightGray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun underlinedFormField(
    value : String,
    onValueChange: (String) -> Unit,
    label: String
){
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text( text = label, style = MaterialTheme.typography.bodySmall ) },
        textStyle = TextStyle(color = Color.White),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.LightGray,
            disabledIndicatorColor = Color.Gray
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun passwordTextField(
    Password : String,
    onPasswordChange : (String) -> Unit,
    label : String
){
    var passwordVisible by remember { mutableStateOf(false) }

    TextField(
        value = Password,
        onValueChange = onPasswordChange,
        label = { Text(label) },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            focusedIndicatorColor = Color.DarkGray,
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.LightGray
        ),
        visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
            val description = if (passwordVisible) "Hide Password" else "Show Password"

            IconButton(onClick = {passwordVisible = !passwordVisible}) {
                Icon(imageVector = icon, contentDescription = description)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(selectedDate: String, onDateSelected: (String) -> Unit) {
    val context = LocalContext.current                      // Gets the current activity context
    val calendar = Calendar.getInstance()                   // Current date

    val datePickerDialog = DatePickerDialog(
        context,                                            // Context to show the dialog
        { _, year, month, day ->                            // Callback when user selects a date
            onDateSelected("$day/${month + 1}/$year")       // Formats the date string and sends it back
        },
        calendar.get(Calendar.YEAR),                        // Default year
        calendar.get(Calendar.MONTH),                       // Default month
        calendar.get(Calendar.DAY_OF_MONTH)                 // Default day
    )

    Box(
        modifier = Modifier
            .clickable { datePickerDialog.show() }          // Shows the dialog when clicked
    ) {
        TextField(
            value = selectedDate,
            onValueChange = {},                                 // Read-only
            readOnly = true,
            label = { Text("Select Date") },
            enabled = false,
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.White,
                unfocusedLabelColor = Color.White,
                disabledContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.LightGray,
                disabledLabelColor = Color.LightGray
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerField(
    selectedTime: String,
    onTimeSelected: (String) -> Unit,
    label: String
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            // Format time as HH:mm
            val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
            onTimeSelected(formattedTime)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true // 24-hour format
    )

    Box(
        modifier = Modifier
            .clickable { timePickerDialog.show() }
    ) {
        TextField(
            value = selectedTime,
            onValueChange = {},
            readOnly = true,
            enabled = false,
            label = { Text(label) },
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.White,
                unfocusedLabelColor = Color.White,
                disabledContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.LightGray,
                disabledLabelColor = Color.LightGray
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor(),
            textStyle = TextStyle(color = Color.White),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.White,
                unfocusedLabelColor = Color.White,
                disabledContainerColor = Color.Transparent,
                disabledIndicatorColor = Color.LightGray,
                disabledLabelColor = Color.LightGray
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(color = Color.Transparent)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.background(Color.Transparent),
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun toggleSwitch(
    label: String,
    checked : Boolean,
    onCheckedChange : (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,                       // circle color when ON
                checkedTrackColor = Color.White,                       // background track when ON
                uncheckedThumbColor = Color.Black,                     // circle color when OFF
                uncheckedTrackColor = Color.White,                     // background track when OFF
                checkedBorderColor = Color.Transparent,                // border when ON
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}

fun dopasswordsmatch( password: String, confirmpassword: String ): Boolean {
    return password == confirmpassword
}

fun emailconstraint( email: String ): Boolean{
    return email.endsWith("@sggs.ac.in", ignoreCase = true)
}

@RequiresApi(Build.VERSION_CODES.O)
fun millicon(
    date: String,
    time: String
): Long {
    val formattedDate = formatDateForParsing(date)
    val formattedTime = formatTimeForParsing(time)

    return LocalDateTime.parse(
        "$formattedDate $formattedTime",
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    )
        .atZone(ZoneId.of("Asia/Kolkata"))
        .toInstant()
        .toEpochMilli()
}

fun formatDateForParsing(date: String): String {
    val parts = date.split("/")
    return parts.joinToString("/") { it.padStart(2, '0') }
}

fun formatTimeForParsing(time: String): String {
    val parts = time.split(":")
    return parts.joinToString(":") { it.padStart(2, '0') }
}