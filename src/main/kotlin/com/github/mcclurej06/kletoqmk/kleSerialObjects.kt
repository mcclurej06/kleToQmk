package com.github.mcclurej06.kletoqmk

@JsModule("@ijprest/kle-serial")
@JsNonModule
external val kle: Kle


val Array<String?>.index:Int
    get() {
        return this[8]!!.toInt()
    }

external class Kle {
    val Serial: Serial
}

external class Serial {
    fun parse(json: String): Keyboard
    fun deserialize(json: dynamic): Keyboard
}

external class Keyboard {
    val meta: KeyboardMetadata
    val keys: Array<Key>
}

external class KeyboardMetadata {
    val author: String
    val backcolor: String
    val background: Background
    val name: String
    val notes: String
    val radii: String
    val switchBrand: String
    val switchMount: String
    val switchType: String
}

external class Background {
    val name: String
    val style: String
}

external class Key {
    val color: String;
    val labels: Array<String?>;
    val textColor: Array<String?>;
    val textSize: Array<Double?>;
    val default: Default

    val x: Double;
    val y: Double;
    val width: Double;
    val height: Double;

    val x2: Double;
    val y2: Double;
    val width2: Double;
    val height2: Double;

    val rotation_x: Double;
    val rotation_y: Double;
    val rotation_angle: Double;

    val decal: Boolean;
    val ghost: Boolean;
    val stepped: Boolean;
    val nub: Boolean;

    val profile: String;

    val sm: String; // switch mount
    val sb: String; // switch brand
    val st: String; // switch type
}

external class Default {
    val textColor: String
    val textSize: Double
}