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
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.serpak.ip_test_task.R
import com.serpak.ip_test_task.entity.ItemEntity
import com.serpak.ip_test_task.ui.theme.Purple40

@Composable
fun ItemScreen(
    viewModel: ItemScreenViewModel = viewModel()
) {

    var searchQuery by remember { mutableStateOf("") }

    val itemList by viewModel.items.observeAsState(emptyList())
    val filteredItemList = itemList.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    //Реализовать поисковую строку
    Column(){
        TextField(
            value = searchQuery,
            onValueChange = { newQuery -> searchQuery = newQuery },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Поиск по названию") }
        )
    }

    LazyColumn(

    ) {
        items(filteredItemList){item ->
            ItemCard(item = item)
        }
    }


}


@Composable
fun ItemCard(
    item: ItemEntity
){
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
                    onClick = { /*TODO*/ } //сделать функцию добавления
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit",
                        tint = Purple40
                    )
                }
                IconButton(
                    onClick = { /*TODO*/}
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "delete",
                        tint = Color.Red
                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            //TagAssistChips(tags = item.tags)
            Text(
                text = item.tags.toString()  //переделать
            )
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
                        text = item.time.toString()
                    )
                }
            }
        }
    }

}

