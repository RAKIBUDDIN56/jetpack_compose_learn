package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var considerList:MutableList<Message> = mutableListOf();
          var surveyList: MutableList<Survey> = mutableListOf();
            considerList.add(Message("Lery","Android development sucks\nBut we have to love it"))
            considerList.add(Message("Setery","Android development sucks\nBut we have to love it"))
            surveyList.add(Survey("Male",false))
            surveyList.add(Survey("Female",false))

            var itemList: MutableList<Item> = mutableListOf()
            itemList.add(Item(R.drawable.hulk,"One"))
            itemList.add(Item(R.drawable.iron,"Second"))
            itemList.add(Item(R.drawable.superman,"Three"))
            itemList.add(Item(R.drawable.hulk,"Four"))
            itemList.add(Item(R.drawable.hulk,"Fine"))
            MyApplicationTheme {
              Column {
                  Conversation(messages = considerList)

                  SurveyAnswerCard(surveyList)
                  Spacer(modifier = Modifier.height(4.dp))
                  SurveyAnswerCard(surveyList)

                  ScorrableRow(Modifier.padding(),itemList)

              }
            }

        }

    }
}

data class Message(val author:String, val body: String)
@Composable
fun MessageCard(msg: Message){
    Row (modifier = Modifier.padding(8.dp)){

        Image(painter = painterResource(id = R.drawable.hulk), contentDescription = "Launcher", modifier = Modifier
            .size(40.dp)
            .clip(
                CircleShape
            )
            .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape))
        Spacer(modifier = Modifier.width(8.dp))
      var isExpanded by remember {
          mutableStateOf(false)
      }
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = ""
        )

        Column(Modifier.clickable { isExpanded=!isExpanded }) {
            Text(text = msg.author,color=MaterialTheme.colorScheme.secondary, style = MaterialTheme.typography.titleLarge);
            Spacer(modifier = Modifier.height(4.dp))
            Surface( shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp, color = surfaceColor, modifier = Modifier
                .animateContentSize()
                .padding(1.dp)) {
                Text(text = msg.body,Modifier.padding(4.dp), maxLines = if(isExpanded) Int.MAX_VALUE else 1,style=MaterialTheme.typography.bodyMedium)
            };
        }
    }
}
data class Survey(val name:String, val isSelected: Boolean)
@Composable
fun SurveyAnswer(survey:Survey) {


    var selected: Survey? by rememberSaveable {
        mutableStateOf(null)
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(survey.name)
        RadioButton(false, onClick = { selected!!.isSelected != selected!!.isSelected })
    }
 }

@Composable
fun SurveyAnswerCard(surveys:List<Survey>){
    Card(modifier = Modifier.padding(8.dp)) {

        LazyColumn() {
            items(surveys) { survey ->
                SurveyAnswer(survey = survey)
            }
        }
    }

}
data class  Item(val drawable:Int, val title:String)
@Composable
fun ScorrableRow(modifier: Modifier=Modifier,items:List<Item>){
    LazyRow(modifier = modifier, contentPadding = PaddingValues(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.Top){
        items(items){
            item ->ItemCard(item)
        }
    }
}
@Composable
fun ItemCard(item:Item){
   Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {

       Image(painter = painterResource(id = item.drawable), contentDescription = null, modifier = Modifier.size(100.dp).clip(
           CircleShape))
       Text(item.title, textAlign = TextAlign.Center)
   }


}





@Composable
fun Conversation(messages:List<Message>){
    LazyColumn{
        items(messages){
            message->
            MessageCard(msg = message)
            
        }

    }
}
@Preview
@Composable
fun PreviewMessageCard(){

    MessageCard(msg = Message("Lexi","Take a look at Jetpack"))
}