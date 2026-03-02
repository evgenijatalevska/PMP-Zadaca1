package com.example.favoritetwittersearches

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.favoritetwittersearches.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Ова ја поставува темата на апликацијата
            AppTheme() {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TwitterSearchApp()
                }
            }
        }
    }
}

@Composable
fun TwitterSearchApp() {
    // 1. Променливи за чување на тоа што го пишуваш
    var queryText by remember { mutableStateOf("") }
    var tagText by remember { mutableStateOf("") }

    // 2. Листата каде ќе се додаваат пребарувањата
    val searchList = remember { mutableStateListOf<TaggedSearch>() }

    Column(modifier = Modifier.padding(16.dp)) {
        // Поле за Query
        OutlinedTextField(
            value = queryText,
            onValueChange = { queryText = it },
            label = { Text("Enter Twitter search query here") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ред со Поле за Tag и копче Save
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = tagText,
                onValueChange = { tagText = it },
                label = { Text("Tag your query") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (queryText.isNotBlank() && tagText.isNotBlank()) {
                    searchList.add(TaggedSearch(tagText, queryText))
                    queryText = ""; tagText = "" // Чистиме полиња по зачувување
                }
            }) {
                Text("Save")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Tagged Searches", style = MaterialTheme.typography.headlineSmall)

        // 3. Листа која ги покажува зачуваните работи
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(searchList) { item ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Text(item.tag, modifier = Modifier.weight(1f))
                        Text("Edit", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        // Копче за чистење на сè
        Button(
            onClick = { searchList.clear() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear Tags")
        }
    }

}