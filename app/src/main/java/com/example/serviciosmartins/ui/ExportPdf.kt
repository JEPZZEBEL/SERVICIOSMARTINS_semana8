package com.example.serviciosmartins.ui

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.example.serviciosmartins.data.ServiceEntity
import java.io.File
import java.io.FileOutputStream

fun exportServicesPdf(context: Context, services: List<ServiceEntity>): File {
    val doc = PdfDocument()
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4-ish
    val page = doc.startPage(pageInfo)

    val canvas = page.canvas
    val paint = Paint().apply { textSize = 14f }

    var y = 50
    paint.textSize = 18f
    canvas.drawText("Servicios Martins - Reporte", 40f, y.toFloat(), paint)

    y += 30
    paint.textSize = 12f
    canvas.drawText("Total: ${services.size}", 40f, y.toFloat(), paint)

    y += 30
    paint.textSize = 12f

    services.take(35).forEachIndexed { idx, s ->
        val line = "${idx + 1}. ${s.title} | ${s.category} | ${s.phone}"
        canvas.drawText(line, 40f, y.toFloat(), paint)
        y += 18
        if (y > 800) return@forEachIndexed
    }

    doc.finishPage(page)

    val outDir = File(context.cacheDir, "exports").apply { mkdirs() }
    val outFile = File(outDir, "servicios_martins_${System.currentTimeMillis()}.pdf")

    FileOutputStream(outFile).use { doc.writeTo(it) }
    doc.close()
    return outFile
}

fun shareFile(context: Context, file: File) {
    val uri = FileProvider.getUriForFile(
        context,
        context.packageName + ".fileprovider",
        file
    )
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(intent, "Compartir PDF"))
}
