package com.serpak.ip_test_task.ui


import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.lifecycle.viewmodel.compose.viewModel
import com.serpak.ip_test_task.screen.ItemScreenViewModel

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.serpak.ip_test_task.R
import com.serpak.ip_test_task.entity.ItemEntity
import com.serpak.ip_test_task.ui.theme.Purple40
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ItemScreen(
    viewModel: ItemScreenViewModel = hiltViewModel()
) {

    var searchQuery by remember { mutableStateOf("") }

    val itemList by viewModel.items.observeAsState(emptyList())
    val filteredItemList = itemList.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    //Реализовать поисковую строку
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = "Список товаров",
            style = TextStyle(
                fontSize = 25.sp
            )
        )
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newQuery -> searchQuery = newQuery },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            label = { Text("Поиск по названию") }
        )
        LazyColumn {
            items(filteredItemList){item ->
                ItemCard(item = item)
            }
        }

    }

}

@Composable
fun ItemCard(
    item: ItemEntity,
    viewModel: ItemScreenViewModel = hiltViewModel()
){
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember {mutableStateOf(false)}

    val formattedDate = formatTimestamp(item.time.toLong())
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.name,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                IconButton(
                    onClick = { showDialog = true }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit",
                        tint = Purple40
                    )
                }
                IconButton(
                    onClick = { showDeleteDialog = true }
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "delete",
                        tint = Color.Red
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            TagsRow(tags = item.tags)
            Spacer(modifier = Modifier.height(10.dp))
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column (
                    modifier = Modifier.weight(0.5f)
                ){
                    Text(
                        text = "На складе",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = item.amount.toString()
                    )
                }
                Column(
                    modifier = Modifier.weight(0.5f)
                ) {
                    Text(
                        text = "Дата добавления",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = formattedDate
                    )
                }
            }
        }
    }
    if (showDialog) {
        EditAmountDialog(
            initialAmount = item.amount,
            onDismiss = { showDialog = false },
            onConfirm = { newAmount ->
                viewModel.updateItemAmount(item.id, newAmount) // Обновление количества через ViewModel
                showDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onDismiss = { showDeleteDialog = false },
            onConfirm = {
                viewModel.deleteItemById(item.id)
                showDeleteDialog = false
            }
        )
    }

}

@Composable
fun TagsRow(tags: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { tag ->
            AssistChip(
                onClick = { /* TODO */ },
                label = { Text(text = tag) }
            )
        }
    }
}

@Composable
fun EditAmountDialog(
    initialAmount: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var amount by remember { mutableStateOf(initialAmount) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
               Icon(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "")
        },
        title = { Text(text = "Количество товара") },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(onClick = { if (amount > 0) amount-- }) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Decrease")
                }
                Text(
                    text = amount.toString(),
                    modifier = Modifier.padding(16.dp),
                    style = TextStyle(fontSize = 20.sp)
                )
                IconButton(onClick = { amount++ }) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Increase")
                }
            }
        },
        confirmButton = {
            Button(onClick = { onConfirm(amount) }) {
                Text("Принять")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}
@Composable
fun DeleteConfirmationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(painter = painterResource(id = R.drawable.ic_warning), contentDescription = "")
        },
        title = { Text(text = "Удаление товара") },
        text = { Text(text = "Вы действительно хотите удалить выбранный товар?") },
        confirmButton = {
            Button(onClick = { onConfirm() }) {
                Text("Да")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Нет")
            }
        }
    )
}


fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val format = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
    return format.format(date)
}


