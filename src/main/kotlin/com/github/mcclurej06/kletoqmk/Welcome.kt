package com.github.mcclurej06.kletoqmk

import csstype.*
import emotion.react.css
import react.FC
import react.Props
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.PropsWithClassName
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.textarea
import react.useState

external interface WelcomeProps : Props {
    var inputJsonString: String
    var outputJsonString: String
}

val json = Json { prettyPrint = true }

val Welcome = FC<WelcomeProps> { props ->
    var inputJsonString by useState(props.inputJsonString)
    var outputJsonString by useState(props.outputJsonString)

    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            justifyContent = JustifyContent.spaceBetween
            alignItems = AlignItems.flexStart
            width = 50.em
        }
        textarea {
            textarea()
            cols = 50
            rows = 20
            value = inputJsonString
            onChange = { event ->
                inputJsonString = event.target.value
            }
        }

        input() {
            css {
                alignSelf = AlignSelf.flexEnd
            }
            type = InputType.button
            value = "Convert"
            onClick = {
                println(inputJsonString)
                val keyboard = kle.Serial.parse(inputJsonString)
                val layoutItems: List<LayoutItem> = keyboard
                    .keys
                    .sortedBy { it.labels.index }
                    .map(Key::toLayoutItem)

                outputJsonString = json.encodeToString<List<LayoutItem>>(layoutItems)
            }
        }

        textarea {
            textarea()
            readOnly = true
            cols = 50
            rows = 20
            value = outputJsonString
        }
    }
}

private fun PropsWithClassName.textarea() {
    css {
        width = 100.pct
    }
}