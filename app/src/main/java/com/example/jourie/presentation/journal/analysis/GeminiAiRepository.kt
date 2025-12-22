package com.example.jourie.presentation.journal.analysis

import android.util.Log
import com.example.jourie.BuildConfig
import com.google.ai.client.generativeai.GenerativeModel

/**
 * Simple wrapper around the Gemini client used by JournalAnalysisViewModel. For now it returns a
 * very lightweight mapping of the model's text output into the fields expected by the screen.
 */
class GeminiAiRepository {

        private val logTag = "GeminiAiRepository"

        private val model =
                GenerativeModel(modelName = "gemini-2.5-flash", apiKey = BuildConfig.GEMINI_API_KEY)

        data class JournalAiResult(
                val prediction: String,
                val rootCause: String,
                val recommendation: String,
                val quote: String,
                val keywords: List<String>,
                val emotionDistribution: Map<String, Float>
        )

        suspend fun analyzeJournal(content: String): JournalAiResult {
                Log.d(
                        logTag,
                        "Starting Gemini analysis. model=gemini-2.5-flash, apiKeySet=${BuildConfig.GEMINI_API_KEY.isNotBlank()}"
                )

                val prompt = buildString {
                        appendLine("Kamu adalah asisten jurnal kesehatan mental yang membantu.")
                        appendLine(
                                "Analisis entri jurnal berikut dan kembalikan hasil analysis dalam format JSON STRICT."
                        )
                        appendLine(
                                "Jangan gunakan markdown formatting. Berikan raw JSON string saja."
                        )
                        appendLine()
                        appendLine("ATURAN PENTING JSON:")
                        appendLine("1. Pastikan JSON valid rfc8259.")
                        appendLine(
                                "2. PENTING: Value string TIDAK BOLEH mengandung unescaped double quotes (\")."
                        )
                        appendLine("   Salah: \"quote\": \"Kata \"Mutiara\" - Tokoh\"")
                        appendLine("   Benar: \"quote\": \"Kata \\\"Mutiara\\\" - Tokoh\"")
                        appendLine(
                                "   Atau ganti tanda kutip ganda di dalam teks dengan tanda kutip tunggal (')."
                        )
                        appendLine()
                        appendLine("Entri jurnal:")
                        appendLine(content)
                        appendLine()
                        appendLine("Format JSON target:")
                        appendLine("{")
                        appendLine("  \"prediction\": \"Prediksi...\",")
                        appendLine("  \"rootCause\": \"Akar penyebab...\",")
                        appendLine("  \"recommendation\": \"Saran...\",")
                        appendLine("  \"quote\": \"Isi kutipan - Nama Tokoh\",")
                        appendLine("  \"keywords\": [\"kata1\", \"kata2\"],")
                        appendLine("  \"emotionDistribution\": {")
                        appendLine("    \"Senang\": 0.1,")
                        appendLine("    \"Tenang\": 0.2,")
                        appendLine("    \"Sedih\": 0.3,")
                        appendLine("    \"Cemas\": 0.4")
                        appendLine("  }")
                        appendLine("}")
                        appendLine("Pastikan total emotionDistribution mendekati 1.0.")
                        appendLine("Gunakan Bahasa Indonesia yang natural dan empatik.")
                }

                return try {
                        val response = model.generateContent(prompt)
                        var text = response.text?.trim().orEmpty()

                        Log.d(logTag, "Gemini parsing JSON response...")
                        Log.d(logTag, "Gemini raw text: $text")

                        // Bersihkan markdown formatting jika ada
                        if (text.startsWith("```json")) {
                                text = text.removePrefix("```json").removeSuffix("```").trim()
                        } else if (text.startsWith("```")) {
                                text = text.removePrefix("```").removeSuffix("```").trim()
                        }

                        val jsonObject = org.json.JSONObject(text)

                        val prediction = jsonObject.optString("prediction", "")
                        val rootCause = jsonObject.optString("rootCause", "")
                        val recommendation = jsonObject.optString("recommendation", "")
                        val quote = jsonObject.optString("quote", "")

                        // Parse Keywords
                        val keywordsList = mutableListOf<String>()
                        val keywordsArray = jsonObject.optJSONArray("keywords")
                        if (keywordsArray != null) {
                                for (i in 0 until keywordsArray.length()) {
                                        keywordsList.add(keywordsArray.getString(i))
                                }
                        }

                        // Parse Emotion Distribution
                        val emotionMap = mutableMapOf<String, Float>()
                        val emotionObj = jsonObject.optJSONObject("emotionDistribution")
                        if (emotionObj != null) {
                                val keys = emotionObj.keys()
                                while (keys.hasNext()) {
                                        val key = keys.next()
                                        emotionMap[key] = emotionObj.getDouble(key).toFloat()
                                }
                        }

                        val result =
                                JournalAiResult(
                                        prediction = prediction,
                                        rootCause = rootCause,
                                        recommendation = recommendation,
                                        quote = quote,
                                        keywords = keywordsList,
                                        emotionDistribution = emotionMap
                                )
                        Log.d(logTag, "Parsed Result success.")
                        result
                } catch (e: Exception) {
                        Log.e(logTag, "Error calling Gemini API or parsing JSON", e)
                        // Fallback structure
                        JournalAiResult(
                                prediction = "Analisis terganggu",
                                rootCause = "Terjadi kesalahan sistem",
                                recommendation =
                                        "Maaf, kami tidak dapat menganalisis jurnal ini saat ini. Silakan coba lagi nanti. Error: ${e.message}",
                                quote = "",
                                keywords = emptyList(),
                                emotionDistribution = emptyMap()
                        )
                }
        }
}
