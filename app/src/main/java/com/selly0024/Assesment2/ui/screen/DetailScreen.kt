package com.selly0024.Assesment2.ui.screen

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.selly0024.Assesment2.R
import com.selly0024.Assesment2.database.WaterDb
import com.selly0024.Assesment2.ui.theme.MyApplicationTheme
import com.selly0024.Assesment2.util.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Long? = null) {
    val context = LocalContext.current
    val db = WaterDb.getInstance(context)
    val factory = ViewModelFactory(context)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    var jumlah by remember { mutableStateOf("") }
    var waktu by remember { mutableStateOf("") }
    var wadah by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        if (id == null) return@LaunchedEffect
        val data = viewModel.getWaterById(id) ?: return@LaunchedEffect
        jumlah = data.jumlah.toString()
        waktu = data.waktu
        wadah = data.wadah
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.kembali),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(
                        text = if (id == null) stringResource(R.string.tambah_data_air)
                        else stringResource(R.string.edit_data_air)
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        if (jumlah.isEmpty() || waktu.isEmpty() || wadah.isEmpty()) {
                            Toast.makeText(context, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                            return@IconButton
                        }

                        if (id == null) {
                            viewModel.insert(jumlah.toInt(), waktu, wadah)
                        } else {
                            viewModel.update(id, jumlah.toInt(), waktu, wadah)
                        }
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Check,
                            contentDescription = stringResource(R.string.simpan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    if (id != null) {
                        DeleteAction {
                            showDialog = true
                        }
                    }
                }
            )
        }
    ) { padding ->
        FormWater(
            jumlah = jumlah,
            onJumlahChange = { jumlah = it },
            waktu = waktu,
            onWaktuChange = { waktu = it },
            wadah = wadah,
            onWadahChange = { wadah = it },
            modifier = Modifier.padding(padding)
        )
    }

    if (id != null && showDialog) {
        DisplayAlertDialog(
            onDismissRequest = { showDialog = false }) {
            showDialog = false
            viewModel.softdelete(id)
            navController.popBackStack()
        }
    }
}

@Composable
fun DeleteAction(delete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick ={expanded = true} ) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.lainnya),
            tint = MaterialTheme.colorScheme.primary
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(id = R.string.hapus))
                },
                onClick = {
                    expanded = false
                    delete()
                }
            )
        }
    }
}

@Composable
fun FormWater(
    jumlah: String, onJumlahChange: (String) -> Unit,
    waktu: String, onWaktuChange: (String) -> Unit,
    wadah: String, onWadahChange: (String) -> Unit,
    modifier: Modifier
) {
    val waktuList = listOf("Pagi", "Siang", "Sore", "Malam")
    val wadahList = listOf("Botol", "Gelas", "Tumbler", "Lainnya")
    var dropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = jumlah,
            onValueChange = onJumlahChange,
            label = { Text("Jumlah (ml)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Text("Pilih Waktu Minum:", style = MaterialTheme.typography.bodyMedium)
        Column {
            waktuList.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onWaktuChange(item) }
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = waktu == item,
                        onClick = { onWaktuChange(item) }
                    )
                    Text(text = item, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }

        Text("Pilih Jenis Wadah:", style = MaterialTheme.typography.bodyMedium)

        Box {
            OutlinedButton(
                onClick = { dropdownExpanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (wadah.isEmpty()) "Pilih Wadah" else wadah)
            }
            DropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                wadahList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onWadahChange(item)
                            dropdownExpanded = false
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Preview(uiMode = UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun DetailWaterScreenPreview() {
    MyApplicationTheme {
        DetailScreen(rememberNavController())
    }
}