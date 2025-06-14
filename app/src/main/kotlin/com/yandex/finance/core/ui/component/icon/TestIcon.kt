package io.github.composegears.valkyrie

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Stable
val Icons.TestIcon: ImageVector by lazy {
    ImageVector.Builder(
        name = "Person",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(fill = SolidColor(Color(0xFFD4FAE6))) {
            moveTo(12f, 0f)
            lineTo(12f, 0f)
            arcTo(12f, 12f, 0f, isMoreThanHalf = false, isPositiveArc = true, 24f, 12f)
            lineTo(24f, 12f)
            arcTo(12f, 12f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 24f)
            lineTo(12f, 24f)
            arcTo(12f, 12f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 12f)
            lineTo(0f, 12f)
            arcTo(12f, 12f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12f, 0f)
            close()
        }
        path(fill = SolidColor(Color(0xFF795548))) {
            moveTo(9.668f, 20.979f)
            horizontalLineTo(5.979f)
            verticalLineTo(15.293f)
            curveTo(5.979f, 14.708f, 6.458f, 14.229f, 7.043f, 14.229f)
            horizontalLineTo(8.603f)
            curveTo(9.188f, 14.229f, 9.666f, 14.708f, 9.666f, 15.293f)
            verticalLineTo(20.979f)
            horizontalLineTo(9.668f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(8.605f, 14.679f)
            curveTo(8.944f, 14.679f, 9.218f, 14.955f, 9.218f, 15.293f)
            verticalLineTo(20.529f)
            horizontalLineTo(6.43f)
            verticalLineTo(15.293f)
            curveTo(6.43f, 14.954f, 6.706f, 14.679f, 7.043f, 14.679f)
            horizontalLineTo(8.605f)
            close()
            moveTo(8.605f, 14.229f)
            horizontalLineTo(7.045f)
            curveTo(6.46f, 14.229f, 5.981f, 14.708f, 5.981f, 15.293f)
            verticalLineTo(20.979f)
            horizontalLineTo(9.668f)
            verticalLineTo(15.293f)
            curveTo(9.668f, 14.708f, 9.19f, 14.229f, 8.605f, 14.229f)
            close()
        }
        path(
            fill = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0xFF8BC34A),
                    0.789f to Color(0xFF558B2F),
                    1f to Color(0xFF558B2F)
                ),
                center = Offset(5.881f, 7.723f),
                radius = 7.412f
            )
        ) {
            moveTo(2.837f, 11.754f)
            curveTo(2.933f, 9.647f, 6.343f, 3.833f, 6.343f, 3.833f)
            curveTo(6.526f, 3.294f, 7.537f, 3.294f, 7.72f, 3.833f)
            curveTo(7.72f, 3.833f, 11.029f, 9.699f, 11.021f, 11.801f)
            curveTo(11.015f, 13.874f, 10.511f, 16.749f, 7.031f, 16.749f)
            curveTo(3.449f, 16.749f, 2.741f, 13.824f, 2.837f, 11.754f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(7.031f, 3.911f)
            curveTo(7.174f, 3.911f, 7.256f, 3.966f, 7.264f, 3.987f)
            lineTo(7.279f, 4.029f)
            lineTo(7.301f, 4.068f)
            curveTo(8.203f, 5.67f, 10.544f, 10.179f, 10.54f, 11.799f)
            curveTo(10.531f, 14.847f, 9.415f, 16.268f, 7.031f, 16.268f)
            curveTo(5.879f, 16.268f, 4.981f, 15.938f, 4.36f, 15.288f)
            curveTo(3.361f, 14.243f, 3.278f, 12.639f, 3.317f, 11.775f)
            curveTo(3.392f, 10.146f, 5.827f, 5.664f, 6.758f, 4.076f)
            lineTo(6.782f, 4.034f)
            lineTo(6.797f, 3.987f)
            curveTo(6.805f, 3.966f, 6.887f, 3.911f, 7.031f, 3.911f)
            close()
            moveTo(7.031f, 3.429f)
            curveTo(6.733f, 3.429f, 6.434f, 3.564f, 6.343f, 3.833f)
            curveTo(6.343f, 3.833f, 2.933f, 9.647f, 2.837f, 11.754f)
            curveTo(2.741f, 13.824f, 3.449f, 16.749f, 7.031f, 16.749f)
            curveTo(10.511f, 16.749f, 11.015f, 13.874f, 11.021f, 11.801f)
            curveTo(11.027f, 9.699f, 7.72f, 3.833f, 7.72f, 3.833f)
            curveTo(7.628f, 3.564f, 7.33f, 3.429f, 7.031f, 3.429f)
            close()
        }
        path(
            fill = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0xFF9CCC65),
                    0.3f to Color(0xFF96C660),
                    0.383f to Color(0xFF93C35E),
                    0.487f to Color(0xFF86B854),
                    0.818f to Color(0xFF639739),
                    0.995f to Color(0xFF558B2F)
                ),
                center = Offset(4.476f, 17.631f),
                radius = 3.054f
            )
        ) {
            moveTo(2.92f, 20.344f)
            curveTo(2.915f, 20.169f, 2.924f, 19.861f, 3.016f, 19.549f)
            curveTo(3.124f, 19.182f, 3.362f, 18.717f, 3.878f, 18.591f)
            curveTo(3.962f, 18.57f, 4.048f, 18.559f, 4.132f, 18.559f)
            curveTo(4.199f, 18.559f, 4.268f, 18.565f, 4.336f, 18.579f)
            curveTo(4.345f, 18.58f, 4.355f, 18.582f, 4.364f, 18.582f)
            curveTo(4.43f, 18.582f, 4.489f, 18.538f, 4.508f, 18.474f)
            curveTo(4.643f, 18.012f, 5.072f, 17.69f, 5.554f, 17.69f)
            curveTo(5.933f, 17.69f, 6.278f, 17.881f, 6.479f, 18.204f)
            curveTo(6.508f, 18.249f, 6.556f, 18.274f, 6.607f, 18.274f)
            curveTo(6.619f, 18.274f, 6.631f, 18.273f, 6.643f, 18.27f)
            curveTo(6.728f, 18.249f, 6.815f, 18.238f, 6.902f, 18.238f)
            curveTo(6.968f, 18.238f, 7.034f, 18.244f, 7.099f, 18.256f)
            curveTo(7.574f, 18.342f, 7.937f, 18.727f, 7.984f, 19.195f)
            curveTo(7.999f, 19.345f, 7.97f, 19.492f, 7.904f, 19.611f)
            curveTo(7.871f, 19.669f, 7.877f, 19.75f, 7.925f, 19.798f)
            curveTo(8.033f, 19.906f, 8.038f, 20.086f, 7.987f, 20.208f)
            curveTo(7.97f, 20.248f, 7.921f, 20.343f, 7.834f, 20.343f)
            horizontalLineTo(2.92f)
            verticalLineTo(20.344f)
            close()
        }
        path(fill = SolidColor(Color(0xFF689F38))) {
            moveTo(5.554f, 17.841f)
            curveTo(5.881f, 17.841f, 6.179f, 18.007f, 6.352f, 18.285f)
            curveTo(6.407f, 18.375f, 6.505f, 18.426f, 6.607f, 18.426f)
            curveTo(6.631f, 18.426f, 6.655f, 18.423f, 6.679f, 18.417f)
            curveTo(6.752f, 18.399f, 6.827f, 18.39f, 6.902f, 18.39f)
            curveTo(6.959f, 18.39f, 7.016f, 18.395f, 7.073f, 18.405f)
            curveTo(7.475f, 18.477f, 7.796f, 18.816f, 7.835f, 19.211f)
            curveTo(7.847f, 19.33f, 7.825f, 19.446f, 7.774f, 19.538f)
            curveTo(7.688f, 19.69f, 7.745f, 19.83f, 7.819f, 19.905f)
            curveTo(7.855f, 19.941f, 7.873f, 20.005f, 7.867f, 20.074f)
            curveTo(7.861f, 20.139f, 7.835f, 20.181f, 7.823f, 20.194f)
            horizontalLineTo(3.07f)
            curveTo(3.074f, 20.029f, 3.095f, 19.81f, 3.16f, 19.591f)
            curveTo(3.301f, 19.111f, 3.556f, 18.823f, 3.914f, 18.736f)
            curveTo(3.986f, 18.719f, 4.06f, 18.709f, 4.133f, 18.709f)
            curveTo(4.192f, 18.709f, 4.25f, 18.715f, 4.309f, 18.726f)
            curveTo(4.328f, 18.729f, 4.346f, 18.732f, 4.364f, 18.732f)
            curveTo(4.495f, 18.732f, 4.615f, 18.646f, 4.652f, 18.516f)
            curveTo(4.768f, 18.118f, 5.138f, 17.841f, 5.554f, 17.841f)
            close()
            moveTo(5.554f, 17.541f)
            curveTo(4.99f, 17.541f, 4.514f, 17.917f, 4.364f, 18.434f)
            curveTo(4.289f, 18.42f, 4.211f, 18.411f, 4.132f, 18.411f)
            curveTo(4.037f, 18.411f, 3.941f, 18.421f, 3.842f, 18.445f)
            curveTo(3.325f, 18.572f, 3.022f, 18.996f, 2.872f, 19.507f)
            curveTo(2.725f, 20.005f, 2.779f, 20.491f, 2.779f, 20.496f)
            horizontalLineTo(7.835f)
            curveTo(8.15f, 20.496f, 8.296f, 19.959f, 8.032f, 19.695f)
            curveTo(8.03f, 19.694f, 8.165f, 19.499f, 8.134f, 19.184f)
            curveTo(8.081f, 18.646f, 7.657f, 18.208f, 7.127f, 18.112f)
            curveTo(7.051f, 18.099f, 6.977f, 18.093f, 6.904f, 18.093f)
            curveTo(6.802f, 18.093f, 6.703f, 18.105f, 6.608f, 18.129f)
            curveTo(6.388f, 17.775f, 5.998f, 17.541f, 5.554f, 17.541f)
            close()
        }
        path(
            stroke = SolidColor(Color(0xFF689F38)),
            strokeLineWidth = 0.299991f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(6.772f, 18.934f)
            curveTo(6.772f, 18.934f, 6.784f, 18.595f, 6.509f, 18.231f)
        }
        path(
            stroke = SolidColor(Color(0xFF689F38)),
            strokeLineWidth = 0.299991f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(4.384f, 18.574f)
            curveTo(4.705f, 18.58f, 5.018f, 18.831f, 5.065f, 19.14f)
        }
        path(
            stroke = SolidColor(Color(0xFF689F38)),
            strokeLineWidth = 0.299991f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(4.714f, 19.282f)
            curveTo(4.996f, 19.155f, 5.375f, 19.174f, 5.666f, 19.323f)
        }
        path(
            fill = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0xFFFFD54F),
                    0.179f to Color(0xFFFFD34B),
                    0.353f to Color(0xFFFFCE3F),
                    0.524f to Color(0xFFFFC62C),
                    0.693f to Color(0xFFFFBA11),
                    0.778f to Color(0xFFFFB300),
                    1f to Color(0xFFFFB300)
                ),
                center = Offset(13.273f, 6.176f),
                radius = 16.038f
            )
        ) {
            moveTo(13.273f, 6.129f)
            lineTo(6.277f, 12.126f)
            horizontalLineTo(6.776f)
            verticalLineTo(20.971f)
            horizontalLineTo(19.769f)
            verticalLineTo(12.126f)
            horizontalLineTo(20.268f)
            lineTo(13.273f, 6.129f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(13.272f, 6.722f)
            lineTo(19.359f, 11.939f)
            curveTo(19.333f, 11.996f, 19.319f, 12.059f, 19.319f, 12.125f)
            verticalLineTo(20.52f)
            horizontalLineTo(7.226f)
            verticalLineTo(12.126f)
            curveTo(7.226f, 12.06f, 7.211f, 11.996f, 7.185f, 11.94f)
            lineTo(13.272f, 6.722f)
            close()
            moveTo(13.272f, 6.129f)
            lineTo(6.276f, 12.126f)
            horizontalLineTo(6.776f)
            verticalLineTo(20.971f)
            horizontalLineTo(19.768f)
            verticalLineTo(12.126f)
            horizontalLineTo(20.268f)
            lineTo(13.272f, 6.129f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(14.396f, 8.879f)
            curveTo(14.534f, 8.879f, 14.646f, 8.991f, 14.646f, 9.129f)
            verticalLineTo(11.378f)
            curveTo(14.646f, 11.516f, 14.534f, 11.628f, 14.396f, 11.628f)
            horizontalLineTo(12.147f)
            curveTo(12.009f, 11.628f, 11.897f, 11.516f, 11.897f, 11.378f)
            verticalLineTo(9.128f)
            curveTo(11.897f, 8.99f, 12.009f, 8.877f, 12.147f, 8.877f)
            horizontalLineTo(14.396f)
            moveTo(14.396f, 8.579f)
            horizontalLineTo(12.147f)
            curveTo(11.844f, 8.579f, 11.597f, 8.825f, 11.597f, 9.129f)
            verticalLineTo(11.378f)
            curveTo(11.597f, 11.681f, 11.843f, 11.928f, 12.147f, 11.928f)
            horizontalLineTo(14.396f)
            curveTo(14.699f, 11.928f, 14.946f, 11.682f, 14.946f, 11.378f)
            verticalLineTo(9.128f)
            curveTo(14.946f, 8.825f, 14.7f, 8.579f, 14.396f, 8.579f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(17.52f, 13.125f)
            curveTo(17.658f, 13.125f, 17.771f, 13.238f, 17.771f, 13.376f)
            verticalLineTo(16.874f)
            curveTo(17.771f, 17.012f, 17.658f, 17.124f, 17.52f, 17.124f)
            horizontalLineTo(15.27f)
            curveTo(15.132f, 17.124f, 15.02f, 17.012f, 15.02f, 16.874f)
            verticalLineTo(13.376f)
            curveTo(15.02f, 13.238f, 15.132f, 13.125f, 15.27f, 13.125f)
            horizontalLineTo(17.52f)
            close()
            moveTo(17.52f, 12.825f)
            horizontalLineTo(15.27f)
            curveTo(14.967f, 12.825f, 14.72f, 13.071f, 14.72f, 13.376f)
            verticalLineTo(16.874f)
            curveTo(14.72f, 17.177f, 14.966f, 17.424f, 15.27f, 17.424f)
            horizontalLineTo(17.519f)
            curveTo(17.822f, 17.424f, 18.069f, 17.178f, 18.069f, 16.874f)
            verticalLineTo(13.376f)
            curveTo(18.069f, 13.073f, 17.823f, 12.825f, 17.52f, 12.825f)
            close()
        }
        path(
            fill = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0xFFD32F2F),
                    0.437f to Color(0xFFCA2929),
                    0.996f to Color(0xFFB71C1C)
                ),
                center = Offset(10.945f, 12.595f),
                radius = 6.559f
            )
        ) {
            moveTo(13.272f, 19.122f)
            horizontalLineTo(8.775f)
            verticalLineTo(12.876f)
            curveTo(8.775f, 12.738f, 8.888f, 12.626f, 9.026f, 12.626f)
            horizontalLineTo(13.023f)
            curveTo(13.161f, 12.626f, 13.274f, 12.738f, 13.274f, 12.876f)
            verticalLineTo(19.122f)
            horizontalLineTo(13.272f)
            close()
        }
        path(
            fill = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0xFFEF5350),
                    1f to Color(0xFFE53935)
                ),
                center = Offset(11.008f, 12.938f),
                radius = 5.888f
            )
        ) {
            moveTo(12.773f, 18.819f)
            horizontalLineTo(9.275f)
            curveTo(9.206f, 18.819f, 9.15f, 18.764f, 9.15f, 18.694f)
            verticalLineTo(13.055f)
            curveTo(9.15f, 12.986f, 9.206f, 12.93f, 9.275f, 12.93f)
            horizontalLineTo(12.773f)
            curveTo(12.842f, 12.93f, 12.897f, 12.986f, 12.897f, 13.055f)
            verticalLineTo(18.694f)
            curveTo(12.897f, 18.764f, 12.842f, 18.819f, 12.773f, 18.819f)
            close()
        }
        path(fill = SolidColor(Color(0xFF4FC3F7))) {
            moveTo(12.086f, 9.066f)
            horizontalLineTo(14.459f)
            verticalLineTo(11.439f)
            horizontalLineTo(12.086f)
            verticalLineTo(9.066f)
            close()
        }
        path(fill = SolidColor(Color(0xFFEEEEEE))) {
            moveTo(11.899f, 9.128f)
            verticalLineTo(11.376f)
            curveTo(11.899f, 11.514f, 12.011f, 11.627f, 12.149f, 11.627f)
            horizontalLineTo(14.398f)
            curveTo(14.536f, 11.627f, 14.648f, 11.514f, 14.648f, 11.376f)
            verticalLineTo(9.128f)
            curveTo(14.648f, 8.99f, 14.536f, 8.877f, 14.398f, 8.877f)
            horizontalLineTo(12.148f)
            curveTo(12.01f, 8.879f, 11.899f, 8.99f, 11.899f, 9.128f)
            close()
            moveTo(14.396f, 10.127f)
            horizontalLineTo(13.397f)
            verticalLineTo(9.128f)
            horizontalLineTo(14.396f)
            verticalLineTo(10.127f)
            close()
            moveTo(13.148f, 9.128f)
            verticalLineTo(10.127f)
            horizontalLineTo(12.149f)
            verticalLineTo(9.128f)
            horizontalLineTo(13.148f)
            close()
            moveTo(12.148f, 10.377f)
            horizontalLineTo(13.147f)
            verticalLineTo(11.376f)
            horizontalLineTo(12.148f)
            verticalLineTo(10.377f)
            close()
            moveTo(13.397f, 11.376f)
            verticalLineTo(10.377f)
            horizontalLineTo(14.396f)
            verticalLineTo(11.376f)
            horizontalLineTo(13.397f)
            close()
        }
        path(fill = SolidColor(Color(0xFF4FC3F7))) {
            moveTo(15.15f, 13.313f)
            horizontalLineTo(17.642f)
            verticalLineTo(16.935f)
            horizontalLineTo(15.15f)
            verticalLineTo(13.313f)
            close()
        }
        path(
            fill = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0xFFE53935),
                    0.407f to Color(0xFFDC3431),
                    1f to Color(0xFFC62828)
                ),
                center = Offset(10.962f, 13.469f),
                radius = 5.199f
            )
        ) {
            moveTo(12.399f, 15.373f)
            horizontalLineTo(9.651f)
            curveTo(9.582f, 15.373f, 9.526f, 15.318f, 9.526f, 15.249f)
            verticalLineTo(13.5f)
            curveTo(9.526f, 13.431f, 9.582f, 13.376f, 9.651f, 13.376f)
            horizontalLineTo(12.399f)
            curveTo(12.468f, 13.376f, 12.523f, 13.431f, 12.523f, 13.5f)
            verticalLineTo(15.249f)
            curveTo(12.523f, 15.318f, 12.468f, 15.373f, 12.399f, 15.373f)
            close()
        }
        path(
            fill = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0xFFE53935),
                    0.407f to Color(0xFFDC3431),
                    1f to Color(0xFFC62828)
                ),
                center = Offset(10.962f, 13.469f),
                radius = 5.199f
            )
        ) {
            moveTo(12.399f, 18.372f)
            horizontalLineTo(9.651f)
            curveTo(9.582f, 18.372f, 9.526f, 18.316f, 9.526f, 18.247f)
            verticalLineTo(15.999f)
            curveTo(9.526f, 15.93f, 9.582f, 15.875f, 9.651f, 15.875f)
            horizontalLineTo(12.399f)
            curveTo(12.468f, 15.875f, 12.523f, 15.93f, 12.523f, 15.999f)
            verticalLineTo(18.247f)
            curveTo(12.523f, 18.316f, 12.468f, 18.372f, 12.399f, 18.372f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.004f to Color(0xFFBDBDBD),
                    0.215f to Color(0xFFB5B5B5),
                    0.55f to Color(0xFF9E9E9E),
                    0.965f to Color(0xFF787878),
                    1f to Color(0xFF757575)
                ),
                start = Offset(11.027f, 18.831f),
                end = Offset(11.027f, 19.647f)
            )
        ) {
            moveTo(13.775f, 19.538f)
            horizontalLineTo(8.278f)
            verticalLineTo(19.08f)
            curveTo(8.278f, 18.909f, 8.416f, 18.771f, 8.587f, 18.771f)
            horizontalLineTo(13.465f)
            curveTo(13.636f, 18.771f, 13.774f, 18.909f, 13.774f, 19.08f)
            verticalLineTo(19.538f)
            horizontalLineTo(13.775f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF263238)),
            fillAlpha = 0.3f,
            strokeAlpha = 0.3f
        ) {
            moveTo(13.466f, 18.921f)
            curveTo(13.555f, 18.921f, 13.625f, 18.993f, 13.625f, 19.08f)
            verticalLineTo(19.388f)
            horizontalLineTo(8.428f)
            verticalLineTo(19.08f)
            curveTo(8.428f, 18.992f, 8.5f, 18.921f, 8.587f, 18.921f)
            horizontalLineTo(13.466f)
            close()
            moveTo(13.466f, 18.771f)
            horizontalLineTo(8.588f)
            curveTo(8.417f, 18.771f, 8.279f, 18.909f, 8.279f, 19.08f)
            verticalLineTo(19.538f)
            horizontalLineTo(13.777f)
            verticalLineTo(19.08f)
            curveTo(13.775f, 18.909f, 13.637f, 18.771f, 13.466f, 18.771f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(10.841f, 19.08f)
            curveTo(10.852f, 19.054f, 10.818f, 19.014f, 10.764f, 18.99f)
            curveTo(10.71f, 18.967f, 10.656f, 18.97f, 10.645f, 18.997f)
            curveTo(10.634f, 19.024f, 10.668f, 19.064f, 10.722f, 19.087f)
            curveTo(10.777f, 19.11f, 10.83f, 19.107f, 10.841f, 19.08f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(13.195f, 19.328f)
            curveTo(13.211f, 19.308f, 13.194f, 19.267f, 13.156f, 19.237f)
            curveTo(13.119f, 19.207f, 13.076f, 19.198f, 13.06f, 19.218f)
            curveTo(13.043f, 19.238f, 13.061f, 19.279f, 13.098f, 19.309f)
            curveTo(13.135f, 19.34f, 13.179f, 19.348f, 13.195f, 19.328f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(11.062f, 19.285f)
            curveTo(11.133f, 19.285f, 11.191f, 19.257f, 11.191f, 19.222f)
            curveTo(11.191f, 19.188f, 11.133f, 19.159f, 11.062f, 19.159f)
            curveTo(10.99f, 19.159f, 10.933f, 19.188f, 10.933f, 19.222f)
            curveTo(10.933f, 19.257f, 10.99f, 19.285f, 11.062f, 19.285f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(12.17f, 19.278f)
            curveTo(12.241f, 19.278f, 12.299f, 19.25f, 12.299f, 19.215f)
            curveTo(12.299f, 19.18f, 12.241f, 19.152f, 12.17f, 19.152f)
            curveTo(12.099f, 19.152f, 12.041f, 19.18f, 12.041f, 19.215f)
            curveTo(12.041f, 19.25f, 12.099f, 19.278f, 12.17f, 19.278f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(11.905f, 19.11f)
            curveTo(11.954f, 19.11f, 11.993f, 19.086f, 11.993f, 19.056f)
            curveTo(11.993f, 19.026f, 11.954f, 19.002f, 11.905f, 19.002f)
            curveTo(11.856f, 19.002f, 11.816f, 19.026f, 11.816f, 19.056f)
            curveTo(11.816f, 19.086f, 11.856f, 19.11f, 11.905f, 19.11f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(13.347f, 19.153f)
            curveTo(13.418f, 19.153f, 13.475f, 19.119f, 13.475f, 19.077f)
            curveTo(13.475f, 19.035f, 13.418f, 19f, 13.347f, 19f)
            curveTo(13.277f, 19f, 13.22f, 19.035f, 13.22f, 19.077f)
            curveTo(13.22f, 19.119f, 13.277f, 19.153f, 13.347f, 19.153f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(10.292f, 19.08f)
            curveTo(10.341f, 19.08f, 10.381f, 19.06f, 10.381f, 19.036f)
            curveTo(10.381f, 19.012f, 10.341f, 18.993f, 10.292f, 18.993f)
            curveTo(10.243f, 18.993f, 10.204f, 19.012f, 10.204f, 19.036f)
            curveTo(10.204f, 19.06f, 10.243f, 19.08f, 10.292f, 19.08f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(12.843f, 19.185f)
            curveTo(12.921f, 19.173f, 12.98f, 19.133f, 12.974f, 19.094f)
            curveTo(12.969f, 19.056f, 12.9f, 19.034f, 12.822f, 19.046f)
            curveTo(12.743f, 19.058f, 12.684f, 19.098f, 12.69f, 19.137f)
            curveTo(12.696f, 19.176f, 12.764f, 19.197f, 12.843f, 19.185f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(10.511f, 19.274f)
            curveTo(10.586f, 19.256f, 10.639f, 19.212f, 10.631f, 19.175f)
            curveTo(10.622f, 19.138f, 10.554f, 19.122f, 10.479f, 19.139f)
            curveTo(10.404f, 19.157f, 10.35f, 19.201f, 10.359f, 19.239f)
            curveTo(10.368f, 19.276f, 10.436f, 19.291f, 10.511f, 19.274f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(12.4f, 19.101f)
            curveTo(12.497f, 19.101f, 12.577f, 19.072f, 12.577f, 19.036f)
            curveTo(12.577f, 19.001f, 12.497f, 18.972f, 12.4f, 18.972f)
            curveTo(12.302f, 18.972f, 12.223f, 19.001f, 12.223f, 19.036f)
            curveTo(12.223f, 19.072f, 12.302f, 19.101f, 12.4f, 19.101f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(11.363f, 19.044f)
            curveTo(11.412f, 19.011f, 11.44f, 18.968f, 11.426f, 18.946f)
            curveTo(11.412f, 18.925f, 11.361f, 18.934f, 11.312f, 18.966f)
            curveTo(11.262f, 18.999f, 11.234f, 19.042f, 11.248f, 19.064f)
            curveTo(11.262f, 19.085f, 11.314f, 19.076f, 11.363f, 19.044f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(11.827f, 19.281f)
            curveTo(11.843f, 19.245f, 11.782f, 19.181f, 11.689f, 19.138f)
            curveTo(11.597f, 19.096f, 11.508f, 19.09f, 11.492f, 19.126f)
            curveTo(11.475f, 19.162f, 11.536f, 19.226f, 11.629f, 19.269f)
            curveTo(11.721f, 19.312f, 11.81f, 19.317f, 11.827f, 19.281f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(9.808f, 19.385f)
            curveTo(9.825f, 19.365f, 9.807f, 19.325f, 9.77f, 19.294f)
            curveTo(9.733f, 19.264f, 9.69f, 19.256f, 9.673f, 19.275f)
            curveTo(9.657f, 19.295f, 9.674f, 19.336f, 9.712f, 19.367f)
            curveTo(9.749f, 19.397f, 9.792f, 19.405f, 9.808f, 19.385f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(8.785f, 19.335f)
            curveTo(8.856f, 19.335f, 8.914f, 19.307f, 8.914f, 19.272f)
            curveTo(8.914f, 19.237f, 8.856f, 19.209f, 8.785f, 19.209f)
            curveTo(8.714f, 19.209f, 8.656f, 19.237f, 8.656f, 19.272f)
            curveTo(8.656f, 19.307f, 8.714f, 19.335f, 8.785f, 19.335f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(8.519f, 19.168f)
            curveTo(8.568f, 19.168f, 8.608f, 19.144f, 8.608f, 19.114f)
            curveTo(8.608f, 19.085f, 8.568f, 19.06f, 8.519f, 19.06f)
            curveTo(8.47f, 19.06f, 8.431f, 19.085f, 8.431f, 19.114f)
            curveTo(8.431f, 19.144f, 8.47f, 19.168f, 8.519f, 19.168f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(9.962f, 19.212f)
            curveTo(10.032f, 19.212f, 10.09f, 19.178f, 10.09f, 19.135f)
            curveTo(10.09f, 19.093f, 10.032f, 19.059f, 9.962f, 19.059f)
            curveTo(9.892f, 19.059f, 9.834f, 19.093f, 9.834f, 19.135f)
            curveTo(9.834f, 19.178f, 9.892f, 19.212f, 9.962f, 19.212f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(9.457f, 19.244f)
            curveTo(9.536f, 19.232f, 9.595f, 19.191f, 9.589f, 19.153f)
            curveTo(9.583f, 19.114f, 9.515f, 19.092f, 9.436f, 19.104f)
            curveTo(9.358f, 19.116f, 9.299f, 19.157f, 9.304f, 19.195f)
            curveTo(9.31f, 19.234f, 9.379f, 19.256f, 9.457f, 19.244f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(9.014f, 19.158f)
            curveTo(9.112f, 19.158f, 9.191f, 19.129f, 9.191f, 19.093f)
            curveTo(9.191f, 19.058f, 9.112f, 19.029f, 9.014f, 19.029f)
            curveTo(8.917f, 19.029f, 8.837f, 19.058f, 8.837f, 19.093f)
            curveTo(8.837f, 19.129f, 8.917f, 19.158f, 9.014f, 19.158f)
            close()
        }
        path(fill = SolidColor(Color(0xFFEEEEEE))) {
            moveTo(15.271f, 13.125f)
            curveTo(15.132f, 13.125f, 15.02f, 13.238f, 15.02f, 13.376f)
            verticalLineTo(16.874f)
            curveTo(15.02f, 17.011f, 15.132f, 17.124f, 15.271f, 17.124f)
            horizontalLineTo(17.519f)
            curveTo(17.657f, 17.124f, 17.769f, 17.011f, 17.769f, 16.874f)
            verticalLineTo(13.376f)
            curveTo(17.769f, 13.238f, 17.657f, 13.125f, 17.519f, 13.125f)
            horizontalLineTo(15.271f)
            close()
            moveTo(16.52f, 13.376f)
            horizontalLineTo(17.519f)
            verticalLineTo(14.375f)
            horizontalLineTo(16.52f)
            verticalLineTo(13.376f)
            close()
            moveTo(15.271f, 13.376f)
            horizontalLineTo(16.27f)
            verticalLineTo(14.375f)
            horizontalLineTo(15.271f)
            verticalLineTo(13.376f)
            close()
            moveTo(15.271f, 14.625f)
            horizontalLineTo(16.27f)
            verticalLineTo(15.624f)
            horizontalLineTo(15.271f)
            verticalLineTo(14.625f)
            close()
            moveTo(16.271f, 16.874f)
            horizontalLineTo(15.272f)
            verticalLineTo(15.875f)
            horizontalLineTo(16.271f)
            verticalLineTo(16.874f)
            close()
            moveTo(17.52f, 16.874f)
            horizontalLineTo(16.521f)
            verticalLineTo(15.875f)
            horizontalLineTo(17.52f)
            verticalLineTo(16.874f)
            close()
            moveTo(16.52f, 15.624f)
            verticalLineTo(14.625f)
            horizontalLineTo(17.519f)
            verticalLineTo(15.624f)
            horizontalLineTo(16.52f)
            close()
        }
        path(fill = SolidColor(Color(0xFFFFC107))) {
            moveTo(12.365f, 16.274f)
            curveTo(12.075f, 16.274f, 11.84f, 16.038f, 11.84f, 15.748f)
            curveTo(11.84f, 15.459f, 12.075f, 15.224f, 12.365f, 15.224f)
            curveTo(12.654f, 15.224f, 12.89f, 15.459f, 12.89f, 15.748f)
            curveTo(12.89f, 16.038f, 12.654f, 16.274f, 12.365f, 16.274f)
            close()
        }
        path(fill = SolidColor(Color(0xFFC62828))) {
            moveTo(12.365f, 15.373f)
            curveTo(12.572f, 15.373f, 12.74f, 15.542f, 12.74f, 15.748f)
            curveTo(12.74f, 15.955f, 12.572f, 16.124f, 12.365f, 16.124f)
            curveTo(12.158f, 16.124f, 11.99f, 15.955f, 11.99f, 15.748f)
            curveTo(11.99f, 15.542f, 12.158f, 15.373f, 12.365f, 15.373f)
            close()
            moveTo(12.365f, 15.073f)
            curveTo(11.993f, 15.073f, 11.69f, 15.377f, 11.69f, 15.748f)
            curveTo(11.69f, 16.121f, 11.993f, 16.423f, 12.365f, 16.423f)
            curveTo(12.737f, 16.423f, 13.04f, 16.121f, 13.04f, 15.748f)
            curveTo(13.04f, 15.377f, 12.737f, 15.073f, 12.365f, 15.073f)
            close()
        }
        path(fill = SolidColor(Color(0xFF795548))) {
            moveTo(17.894f, 17.624f)
            horizontalLineTo(14.897f)
            curveTo(14.759f, 17.624f, 14.646f, 17.511f, 14.646f, 17.373f)
            verticalLineTo(16.874f)
            curveTo(14.646f, 16.736f, 14.759f, 16.623f, 14.897f, 16.623f)
            horizontalLineTo(17.895f)
            curveTo(18.033f, 16.623f, 18.146f, 16.736f, 18.146f, 16.874f)
            verticalLineTo(17.373f)
            curveTo(18.144f, 17.511f, 18.032f, 17.624f, 17.894f, 17.624f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(17.894f, 16.874f)
            verticalLineTo(17.373f)
            horizontalLineTo(14.897f)
            verticalLineTo(16.874f)
            horizontalLineTo(17.894f)
            close()
            moveTo(17.894f, 16.623f)
            horizontalLineTo(14.897f)
            curveTo(14.759f, 16.623f, 14.646f, 16.736f, 14.646f, 16.874f)
            verticalLineTo(17.373f)
            curveTo(14.646f, 17.511f, 14.759f, 17.624f, 14.897f, 17.624f)
            horizontalLineTo(17.895f)
            curveTo(18.033f, 17.624f, 18.146f, 17.511f, 18.146f, 17.373f)
            verticalLineTo(16.874f)
            curveTo(18.144f, 16.736f, 18.032f, 16.623f, 17.894f, 16.623f)
            close()
        }
        path(
            fill = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0xFFF44336),
                    0.34f to Color(0xFFEE3F35),
                    0.803f to Color(0xFFDC3531),
                    0.996f to Color(0xFFD32F2F)
                ),
                center = Offset(13.21f, 5.599f),
                radius = 8.45f
            )
        ) {
            moveTo(20.553f, 11.777f)
            lineTo(13.671f, 5.78f)
            curveTo(13.442f, 5.579f, 13.101f, 5.579f, 12.872f, 5.78f)
            lineTo(5.992f, 11.777f)
            curveTo(5.734f, 12.002f, 5.704f, 12.396f, 5.926f, 12.657f)
            curveTo(6.146f, 12.92f, 6.535f, 12.95f, 6.793f, 12.725f)
            lineTo(13.028f, 7.292f)
            curveTo(13.169f, 7.169f, 13.379f, 7.169f, 13.52f, 7.292f)
            lineTo(19.755f, 12.725f)
            curveTo(19.871f, 12.825f, 20.013f, 12.875f, 20.154f, 12.875f)
            curveTo(20.327f, 12.875f, 20.499f, 12.801f, 20.621f, 12.657f)
            curveTo(20.841f, 12.396f, 20.811f, 12.002f, 20.553f, 11.777f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(13.272f, 6.08f)
            curveTo(13.299f, 6.08f, 13.338f, 6.087f, 13.377f, 6.119f)
            lineTo(20.259f, 12.116f)
            curveTo(20.331f, 12.179f, 20.34f, 12.294f, 20.277f, 12.368f)
            curveTo(20.235f, 12.419f, 20.183f, 12.426f, 20.154f, 12.426f)
            curveTo(20.127f, 12.426f, 20.088f, 12.419f, 20.051f, 12.387f)
            lineTo(13.814f, 6.953f)
            curveTo(13.664f, 6.822f, 13.472f, 6.75f, 13.272f, 6.75f)
            curveTo(13.073f, 6.75f, 12.881f, 6.822f, 12.731f, 6.953f)
            lineTo(6.496f, 12.386f)
            curveTo(6.458f, 12.419f, 6.418f, 12.425f, 6.392f, 12.425f)
            curveTo(6.365f, 12.425f, 6.313f, 12.417f, 6.269f, 12.366f)
            curveTo(6.208f, 12.293f, 6.215f, 12.177f, 6.287f, 12.114f)
            lineTo(13.169f, 6.117f)
            curveTo(13.205f, 6.087f, 13.245f, 6.08f, 13.272f, 6.08f)
            close()
            moveTo(13.272f, 5.63f)
            curveTo(13.13f, 5.63f, 12.987f, 5.679f, 12.872f, 5.78f)
            lineTo(5.992f, 11.777f)
            curveTo(5.734f, 12.002f, 5.704f, 12.396f, 5.926f, 12.657f)
            curveTo(6.047f, 12.801f, 6.22f, 12.875f, 6.392f, 12.875f)
            curveTo(6.533f, 12.875f, 6.676f, 12.825f, 6.791f, 12.725f)
            lineTo(13.026f, 7.292f)
            curveTo(13.097f, 7.23f, 13.185f, 7.2f, 13.272f, 7.2f)
            curveTo(13.359f, 7.2f, 13.448f, 7.23f, 13.518f, 7.292f)
            lineTo(19.754f, 12.725f)
            curveTo(19.869f, 12.825f, 20.012f, 12.875f, 20.153f, 12.875f)
            curveTo(20.325f, 12.875f, 20.498f, 12.801f, 20.619f, 12.657f)
            curveTo(20.84f, 12.395f, 20.81f, 12.002f, 20.553f, 11.777f)
            lineTo(13.671f, 5.78f)
            curveTo(13.557f, 5.681f, 13.415f, 5.63f, 13.272f, 5.63f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.004f to Color(0xFFBDBDBD),
                    0.397f to Color(0xFFBABABA),
                    0.703f to Color(0xFFB0B0B0),
                    0.979f to Color(0xFFA0A0A0),
                    1f to Color(0xFF9E9E9E)
                ),
                start = Offset(11.027f, 19.413f),
                end = Offset(11.027f, 20.223f)
            )
        ) {
            moveTo(14.275f, 20.236f)
            horizontalLineTo(7.778f)
            verticalLineTo(19.711f)
            curveTo(7.778f, 19.531f, 7.924f, 19.386f, 8.104f, 19.386f)
            horizontalLineTo(13.948f)
            curveTo(14.128f, 19.386f, 14.273f, 19.531f, 14.273f, 19.711f)
            verticalLineTo(20.236f)
            horizontalLineTo(14.275f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF263238)),
            fillAlpha = 0.3f,
            strokeAlpha = 0.3f
        ) {
            moveTo(13.949f, 19.536f)
            curveTo(14.047f, 19.536f, 14.125f, 19.615f, 14.125f, 19.711f)
            verticalLineTo(20.086f)
            horizontalLineTo(7.928f)
            verticalLineTo(19.711f)
            curveTo(7.928f, 19.614f, 8.008f, 19.536f, 8.104f, 19.536f)
            horizontalLineTo(13.949f)
            close()
            moveTo(13.949f, 19.386f)
            horizontalLineTo(8.105f)
            curveTo(7.925f, 19.386f, 7.78f, 19.531f, 7.78f, 19.711f)
            verticalLineTo(20.236f)
            horizontalLineTo(14.276f)
            verticalLineTo(19.711f)
            curveTo(14.275f, 19.531f, 14.129f, 19.386f, 13.949f, 19.386f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(13.377f, 19.768f)
            curveTo(13.437f, 19.747f, 13.477f, 19.706f, 13.467f, 19.678f)
            curveTo(13.457f, 19.65f, 13.4f, 19.645f, 13.34f, 19.667f)
            curveTo(13.28f, 19.689f, 13.24f, 19.729f, 13.25f, 19.757f)
            curveTo(13.26f, 19.785f, 13.317f, 19.79f, 13.377f, 19.768f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(10.755f, 19.99f)
            curveTo(10.798f, 19.961f, 10.82f, 19.92f, 10.805f, 19.898f)
            curveTo(10.79f, 19.876f, 10.744f, 19.882f, 10.701f, 19.911f)
            curveTo(10.659f, 19.94f, 10.637f, 19.981f, 10.652f, 20.003f)
            curveTo(10.667f, 20.025f, 10.713f, 20.019f, 10.755f, 19.99f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(13.007f, 19.963f)
            curveTo(13.085f, 19.963f, 13.149f, 19.935f, 13.149f, 19.9f)
            curveTo(13.149f, 19.866f, 13.085f, 19.837f, 13.007f, 19.837f)
            curveTo(12.928f, 19.837f, 12.864f, 19.866f, 12.864f, 19.9f)
            curveTo(12.864f, 19.935f, 12.928f, 19.963f, 13.007f, 19.963f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(11.783f, 19.956f)
            curveTo(11.861f, 19.956f, 11.925f, 19.928f, 11.925f, 19.893f)
            curveTo(11.925f, 19.858f, 11.861f, 19.83f, 11.783f, 19.83f)
            curveTo(11.704f, 19.83f, 11.64f, 19.858f, 11.64f, 19.893f)
            curveTo(11.64f, 19.928f, 11.704f, 19.956f, 11.783f, 19.956f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(12.077f, 19.788f)
            curveTo(12.131f, 19.788f, 12.175f, 19.764f, 12.175f, 19.734f)
            curveTo(12.175f, 19.704f, 12.131f, 19.68f, 12.077f, 19.68f)
            curveTo(12.023f, 19.68f, 11.979f, 19.704f, 11.979f, 19.734f)
            curveTo(11.979f, 19.764f, 12.023f, 19.788f, 12.077f, 19.788f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(10.485f, 19.831f)
            curveTo(10.563f, 19.831f, 10.626f, 19.797f, 10.626f, 19.755f)
            curveTo(10.626f, 19.713f, 10.563f, 19.678f, 10.485f, 19.678f)
            curveTo(10.407f, 19.678f, 10.344f, 19.713f, 10.344f, 19.755f)
            curveTo(10.344f, 19.797f, 10.407f, 19.831f, 10.485f, 19.831f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(13.856f, 19.756f)
            curveTo(13.91f, 19.756f, 13.953f, 19.737f, 13.953f, 19.713f)
            curveTo(13.953f, 19.689f, 13.91f, 19.669f, 13.856f, 19.669f)
            curveTo(13.802f, 19.669f, 13.758f, 19.689f, 13.758f, 19.713f)
            curveTo(13.758f, 19.737f, 13.802f, 19.756f, 13.856f, 19.756f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(11.21f, 19.815f)
            curveTo(11.215f, 19.776f, 11.148f, 19.736f, 11.061f, 19.725f)
            curveTo(10.974f, 19.713f, 10.899f, 19.736f, 10.894f, 19.774f)
            curveTo(10.889f, 19.813f, 10.956f, 19.853f, 11.043f, 19.864f)
            curveTo(11.13f, 19.876f, 11.205f, 19.854f, 11.21f, 19.815f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(13.782f, 19.917f)
            curveTo(13.79f, 19.878f, 13.729f, 19.834f, 13.646f, 19.817f)
            curveTo(13.563f, 19.801f, 13.49f, 19.818f, 13.482f, 19.856f)
            curveTo(13.474f, 19.895f, 13.535f, 19.939f, 13.618f, 19.956f)
            curveTo(13.701f, 19.972f, 13.774f, 19.955f, 13.782f, 19.917f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(11.531f, 19.779f)
            curveTo(11.639f, 19.779f, 11.726f, 19.75f, 11.726f, 19.714f)
            curveTo(11.726f, 19.679f, 11.639f, 19.65f, 11.531f, 19.65f)
            curveTo(11.423f, 19.65f, 11.336f, 19.679f, 11.336f, 19.714f)
            curveTo(11.336f, 19.75f, 11.423f, 19.779f, 11.531f, 19.779f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(12.804f, 19.742f)
            curveTo(12.818f, 19.719f, 12.783f, 19.675f, 12.728f, 19.643f)
            curveTo(12.673f, 19.611f, 12.617f, 19.604f, 12.604f, 19.627f)
            curveTo(12.591f, 19.65f, 12.625f, 19.695f, 12.68f, 19.726f)
            curveTo(12.736f, 19.758f, 12.791f, 19.765f, 12.804f, 19.742f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(12.377f, 19.949f)
            curveTo(12.48f, 19.907f, 12.551f, 19.843f, 12.536f, 19.805f)
            curveTo(12.521f, 19.768f, 12.425f, 19.771f, 12.322f, 19.813f)
            curveTo(12.219f, 19.854f, 12.148f, 19.919f, 12.163f, 19.956f)
            curveTo(12.179f, 19.994f, 12.274f, 19.991f, 12.377f, 19.949f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(9.691f, 19.825f)
            curveTo(9.751f, 19.804f, 9.791f, 19.763f, 9.781f, 19.735f)
            curveTo(9.771f, 19.707f, 9.714f, 19.702f, 9.654f, 19.724f)
            curveTo(9.594f, 19.746f, 9.554f, 19.786f, 9.564f, 19.814f)
            curveTo(9.574f, 19.842f, 9.631f, 19.847f, 9.691f, 19.825f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(9.322f, 20.022f)
            curveTo(9.4f, 20.022f, 9.464f, 19.994f, 9.464f, 19.959f)
            curveTo(9.464f, 19.924f, 9.4f, 19.896f, 9.322f, 19.896f)
            curveTo(9.243f, 19.896f, 9.179f, 19.924f, 9.179f, 19.959f)
            curveTo(9.179f, 19.994f, 9.243f, 20.022f, 9.322f, 20.022f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.08f,
            strokeAlpha = 0.08f
        ) {
            moveTo(8.391f, 19.846f)
            curveTo(8.445f, 19.846f, 8.489f, 19.822f, 8.489f, 19.792f)
            curveTo(8.489f, 19.763f, 8.445f, 19.738f, 8.391f, 19.738f)
            curveTo(8.338f, 19.738f, 8.294f, 19.763f, 8.294f, 19.792f)
            curveTo(8.294f, 19.822f, 8.338f, 19.846f, 8.391f, 19.846f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(10.097f, 19.976f)
            curveTo(10.105f, 19.938f, 10.044f, 19.893f, 9.961f, 19.877f)
            curveTo(9.878f, 19.86f, 9.805f, 19.878f, 9.797f, 19.916f)
            curveTo(9.789f, 19.954f, 9.85f, 19.998f, 9.933f, 20.015f)
            curveTo(10.016f, 20.032f, 10.089f, 20.014f, 10.097f, 19.976f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(9.118f, 19.799f)
            curveTo(9.131f, 19.776f, 9.097f, 19.732f, 9.041f, 19.7f)
            curveTo(8.986f, 19.668f, 8.931f, 19.661f, 8.917f, 19.684f)
            curveTo(8.904f, 19.707f, 8.938f, 19.751f, 8.994f, 19.783f)
            curveTo(9.049f, 19.815f, 9.105f, 19.822f, 9.118f, 19.799f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(8.691f, 20.006f)
            curveTo(8.794f, 19.965f, 8.865f, 19.9f, 8.85f, 19.863f)
            curveTo(8.835f, 19.825f, 8.739f, 19.829f, 8.636f, 19.87f)
            curveTo(8.533f, 19.912f, 8.462f, 19.976f, 8.477f, 20.014f)
            curveTo(8.493f, 20.051f, 8.588f, 20.048f, 8.691f, 20.006f)
            close()
        }
        path(
            fill = Brush.radialGradient(
                colorStops = arrayOf(
                    0f to Color(0xFF9CCC65),
                    0.254f to Color(0xFF96C760),
                    0.599f to Color(0xFF84B851),
                    0.995f to Color(0xFF689F38)
                ),
                center = Offset(17.786f, 18.245f),
                radius = 3.741f
            )
        ) {
            moveTo(15.378f, 20.997f)
            curveTo(15.275f, 20.997f, 15.219f, 20.887f, 15.2f, 20.841f)
            curveTo(15.143f, 20.703f, 15.149f, 20.5f, 15.27f, 20.379f)
            curveTo(15.318f, 20.331f, 15.323f, 20.25f, 15.29f, 20.191f)
            curveTo(15.216f, 20.061f, 15.185f, 19.897f, 15.201f, 19.731f)
            curveTo(15.251f, 19.224f, 15.662f, 18.789f, 16.176f, 18.696f)
            curveTo(16.248f, 18.682f, 16.322f, 18.676f, 16.394f, 18.676f)
            curveTo(16.49f, 18.676f, 16.586f, 18.688f, 16.68f, 18.711f)
            curveTo(16.692f, 18.714f, 16.704f, 18.715f, 16.716f, 18.715f)
            curveTo(16.767f, 18.715f, 16.815f, 18.69f, 16.844f, 18.645f)
            curveTo(17.064f, 18.289f, 17.447f, 18.076f, 17.865f, 18.076f)
            curveTo(18.396f, 18.076f, 18.87f, 18.432f, 19.019f, 18.94f)
            curveTo(19.038f, 19.005f, 19.097f, 19.048f, 19.163f, 19.048f)
            curveTo(19.172f, 19.048f, 19.181f, 19.047f, 19.191f, 19.045f)
            curveTo(19.266f, 19.032f, 19.341f, 19.024f, 19.416f, 19.024f)
            curveTo(19.509f, 19.024f, 19.604f, 19.036f, 19.697f, 19.059f)
            curveTo(20.198f, 19.18f, 20.576f, 19.623f, 20.615f, 20.133f)
            curveTo(20.639f, 20.452f, 20.541f, 20.757f, 20.337f, 20.998f)
            horizontalLineTo(15.378f)
            verticalLineTo(20.997f)
            close()
        }
        path(fill = SolidColor(Color(0xFF689F38))) {
            moveTo(17.865f, 18.226f)
            curveTo(18.33f, 18.226f, 18.744f, 18.537f, 18.875f, 18.983f)
            curveTo(18.912f, 19.113f, 19.032f, 19.198f, 19.163f, 19.198f)
            curveTo(19.181f, 19.198f, 19.2f, 19.197f, 19.218f, 19.192f)
            curveTo(19.284f, 19.181f, 19.35f, 19.173f, 19.415f, 19.173f)
            curveTo(19.496f, 19.173f, 19.578f, 19.184f, 19.659f, 19.203f)
            curveTo(20.098f, 19.309f, 20.428f, 19.697f, 20.463f, 20.142f)
            curveTo(20.483f, 20.398f, 20.412f, 20.644f, 20.262f, 20.847f)
            lineTo(15.38f, 20.848f)
            curveTo(15.368f, 20.842f, 15.33f, 20.8f, 15.317f, 20.718f)
            curveTo(15.305f, 20.646f, 15.312f, 20.547f, 15.374f, 20.485f)
            curveTo(15.449f, 20.412f, 15.504f, 20.271f, 15.419f, 20.118f)
            curveTo(15.36f, 20.013f, 15.335f, 19.881f, 15.348f, 19.746f)
            curveTo(15.392f, 19.305f, 15.75f, 18.926f, 16.202f, 18.843f)
            curveTo(16.265f, 18.831f, 16.329f, 18.826f, 16.392f, 18.826f)
            curveTo(16.476f, 18.826f, 16.56f, 18.837f, 16.643f, 18.857f)
            curveTo(16.667f, 18.862f, 16.691f, 18.865f, 16.715f, 18.865f)
            curveTo(16.817f, 18.865f, 16.914f, 18.813f, 16.97f, 18.725f)
            curveTo(17.166f, 18.413f, 17.499f, 18.226f, 17.865f, 18.226f)
            close()
            moveTo(17.865f, 17.927f)
            curveTo(17.381f, 17.927f, 16.955f, 18.181f, 16.716f, 18.566f)
            curveTo(16.613f, 18.54f, 16.505f, 18.527f, 16.394f, 18.527f)
            curveTo(16.314f, 18.527f, 16.233f, 18.534f, 16.149f, 18.549f)
            curveTo(15.57f, 18.654f, 15.108f, 19.132f, 15.051f, 19.718f)
            curveTo(15.018f, 20.061f, 15.165f, 20.272f, 15.162f, 20.275f)
            curveTo(14.874f, 20.563f, 15.033f, 21.15f, 15.377f, 21.15f)
            horizontalLineTo(20.403f)
            curveTo(20.65f, 20.884f, 20.793f, 20.52f, 20.763f, 20.122f)
            curveTo(20.719f, 19.544f, 20.295f, 19.053f, 19.731f, 18.915f)
            curveTo(19.623f, 18.889f, 19.518f, 18.878f, 19.416f, 18.878f)
            curveTo(19.329f, 18.878f, 19.245f, 18.886f, 19.163f, 18.902f)
            curveTo(18.999f, 18.337f, 18.48f, 17.927f, 17.865f, 17.927f)
            close()
        }
        path(
            stroke = SolidColor(Color(0xFF689F38)),
            strokeLineWidth = 0.299991f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(16.536f, 19.447f)
            curveTo(16.536f, 19.447f, 16.524f, 19.077f, 16.821f, 18.681f)
        }
        path(
            stroke = SolidColor(Color(0xFF689F38)),
            strokeLineWidth = 0.299991f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(19.14f, 19.053f)
            curveTo(18.791f, 19.059f, 18.449f, 19.333f, 18.396f, 19.67f)
        }
        path(
            stroke = SolidColor(Color(0xFF689F38)),
            strokeLineWidth = 0.299991f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(18.782f, 19.825f)
            curveTo(18.475f, 19.686f, 18.061f, 19.707f, 17.743f, 19.869f)
        }
        path(fill = SolidColor(Color(0xFF689F38))) {
            moveTo(2.769f, 20.229f)
            horizontalLineTo(20.768f)
            verticalLineTo(21.429f)
            horizontalLineTo(2.769f)
            verticalLineTo(20.229f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF424242)),
            fillAlpha = 0.2f,
            strokeAlpha = 0.2f
        ) {
            moveTo(2.769f, 20.979f)
            horizontalLineTo(20.768f)
            verticalLineTo(21.429f)
            horizontalLineTo(2.769f)
            verticalLineTo(20.979f)
            close()
        }
    }.build()
}
