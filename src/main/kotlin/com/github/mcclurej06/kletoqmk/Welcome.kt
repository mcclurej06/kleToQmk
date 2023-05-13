package com.github.mcclurej06.kletoqmk

import csstype.*
import emotion.react.css
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.FC
import react.Props
import react.PropsWithClassName
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.textarea
import react.useState

external interface ConvertProps : Props {
    var inputJsonString: String?
    var inputGist: String?
    var outputJsonString: String?
}

val json = Json { prettyPrint = true }
val urlRegex = ".*http://www.keyboard-layout-editor.com/#/gists/([a-z0-9]*).*".toRegex()

val Convert = FC<ConvertProps> { props ->
    var inputJsonString by useState(props.inputJsonString)
    var inputGist by useState(props.inputGist)
    var outputJsonString by useState(props.outputJsonString)

    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            justifyContent = JustifyContent.spaceBetween
            alignItems = AlignItems.flexStart
            width = 50.em
        }
        div {
            +"Gist id or KLE url:"
        }
        div {
            input {

                type = InputType.text
                onChange = { event ->
                    inputGist = event.target.value
                }
            }
            input {
                type = InputType.button
                value = "Load"
                onClick = {
                    println(inputGist)
                    if (!inputGist.isNullOrBlank()) {
                        inputGist?.let {
                            val gistId = if (urlRegex.matches(it)) {
                                urlRegex.replace(it, "$1")
                            } else {
                                it.trim()
                            }
                            axios.get("https://gist.githubusercontent.com/raw/${gistId}/")
                                .then<dynamic> {
                                    JSON.stringify(it.data)?.let {
                                        inputJsonString = it
                                        outputJsonString = convert(it)
                                    }
                                }
                        }
                    }
                }
            }
        }

        div {
            +"KLE json:"
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

        input {
            css {
                alignSelf = AlignSelf.flexEnd
            }
            type = InputType.button
            value = "Convert"
            onClick = {
                println(inputJsonString)
                try {
                    inputJsonString?.let {
                        outputJsonString = convert(it)
                    }
                } catch (e: dynamic) {
                    outputJsonString = "Invalid JSON"

                    println(outputJsonString)
                }
            }
        }

        div {
            +"QMK layout json:"
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

private fun convert(inputJsonString: String): String {
    val keyboard = kle.Serial.parse(inputJsonString)
    val layoutItems: List<LayoutItem> = keyboard
        .keys
        .sortedBy { it.labels.index }
        .map(Key::toLayoutItem)
    return json.encodeToString<List<LayoutItem>>(layoutItems)
}

private fun PropsWithClassName.textarea() {
    css {
        width = 100.pct
    }
}