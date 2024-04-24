package so.cello.sample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cello.cello_sdk.Cello
import so.cello.sample.ui.theme.SampleTheme

val productId = "dev.cello.so"
val productUserId = "5ec7b22c-31ee-4d74-90e4-3f9711c2c239"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val tokenObserver = TokenObserver(productId, productUserId)

            try {
                val token = tokenObserver.getToken() ?: throw IllegalStateException("Token is null")
                Cello.initialize(this, productId, token)
            } catch (e: Exception) {
                Log.d("MainActivity", "Error initializing Cello", e)
                Toast.makeText(this, "Error initializing app features", Toast.LENGTH_LONG).show()
            }

            try {
                Cello.client()?.showFab()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error showing fab", e)
                Toast.makeText(this, "Error showing fab", Toast.LENGTH_LONG).show()
            }
            tokenObserver.startObserving()
        } catch (e: Exception) {
            Log.d("MainActivity", "Error initializing tokenObserver", e)
            Toast.makeText(this, "Error initializing tokenObserver", Toast.LENGTH_LONG).show()
        }

        setContent {
            SampleTheme {
                ContentView()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Cello.client()?.shutdown()
    }
}

@Composable
fun ContentView() {
    var showFab by remember { mutableStateOf(true) }
    var activeUcc by remember { mutableStateOf<Map<String, String>?>(null) }
    val context = LocalContext.current

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        item {
            Text("Settings", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Switch(
                checked = showFab,
                onCheckedChange = {
                    try {
                        showFab = it
                        if (it) Cello.client()?.showFab() else Cello.client()?.hideFab()
                    } catch (e: Exception) {
                        Log.d("MainActivity", "Error switching fab", e)
                        Toast.makeText(context, "Error switching feb", Toast.LENGTH_LONG).show()
                    }
                }
            )
            Text("Show FAB")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                try {
                    Cello.client()?.openWidget()
                } catch (e: Exception) {
                    Log.d("MainActivity", "Error opening widget", e)
                    Toast.makeText(context, "Error opening widget", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Custom Fab launcher")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                try {
                    activeUcc = Cello.client()?.getActiveUcc()
                } catch (e: Exception) {
                    Log.d("MainActivity", "Error fetching UCC", e)
                    Toast.makeText(context, "Error fetching UCC", Toast.LENGTH_LONG).show()
                }
            }) {
                Text("Get Active UCC")
            }
        }

        item {
            Text("Information", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            activeUcc?.let { ucc ->
                ucc.forEach { (key, value) ->
                    Text("$key: $value")
                }
            }
        }
    }
}