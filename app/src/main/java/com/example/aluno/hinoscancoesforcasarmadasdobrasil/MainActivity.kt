package com.example.aluno.hinoscancoesforcasarmadasdobrasil

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * Variáveis para trabalhar com notificações a partir da versão oreo
     */
    private val channelId = "com.example.aluno.hinoscancoesforcasarmadasdobrasil"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // construindo um array com todos os itens da lista para aparecer no spinner

        // futuramente isso poderá ser uma lista recebida do banco de dados
        var musicas = arrayOf(
                "Selecione...",
                "Hino Nacional Brasileiro",
                "Hino À Bandeira Nacional",
                "Hino Da Independência",
                "Hino À Proclamação Da República")


        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, musicas)

        // adicionando o modelo com a lista de opções no spinner
        sMusica.adapter = adapter

        sMusica.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                //val txtMetadata = metadataMusicas[position]

                // pegando o valor do item selecionado no spinner
                val selectedItem = parent.getItemAtPosition(position).toString()

                // para cada posição, iremos adicionar a história e uma imagem ilustrativa
                when (position) {
                    0 -> {
                        // adicionando uma capa quando nenhuma música está selecionada
                        txtResumo.text = ""
                        //tornando a imagem visivel
                        imgMusica.visibility = View.VISIBLE
                        // alterando a imagem. Essa nova imagem está no pacote drawable do projeto
                        imgMusica.setImageResource(R.drawable.hinos_capa)
                    }

                    1 -> {
                        // adicionando o resumo da música no campo de texto
                        txtResumo.text = (selectedItem  +
                                "\nOuviram do Ipiranga as margens plácidas\n" +
                                "De um povo heroico o brado retumbante,\n" +
                                "E o sol da liberdade, em raios fúlgidos,\n" +
                                "Brilhou no céu da pátria nesse instante...\n ")
                        //tornando a imagem visivel
                        imgMusica.visibility = View.VISIBLE
                        // alterando a imagem. Essa nova imagem está no pacote drawable do projeto
                        imgMusica.setImageResource(R.drawable.nacional)
                        // chamando a notificação
                        notificacao()

                    }

                    2 -> {
                        txtResumo.text = (selectedItem +
                                "\nSalve, lindo pendão da esperança, \n" +
                                "Salve, símbolo augusto da paz!\n" +
                                "Tua nobre presença à lembrança\n" +
                                "A grandeza da Pátria nos traz...\n ")
                        imgMusica.visibility = View.VISIBLE
                        imgMusica.setImageResource(R.drawable.saudacao_bandeira)
                        notificacao()
                    }

                    3 -> {
                        txtResumo.text = (selectedItem +
                                "\nJá podeis da Pátria filhos,\n" +
                                "Ver contente a mãe gentil;\n" +
                                "Já raiou a liberdade\n" +
                                "No horizonte do Brasil\n" +
                                "Já raiou a liberdade,\n" +
                                "Já raiou a liberdade\n" +
                                "No horizonte do Brasil.\n" +
                                "Brava gente brasileira!...\n ")
                        imgMusica.visibility = View.VISIBLE
                        imgMusica.setImageResource(R.drawable.independencia)
                        notificacao()
                    }

                    4 -> {
                        txtResumo.text = (selectedItem +
                                "\nSeja um pálio de luz desdobrado.\n" +
                                "Sob a larga amplidão destes céus\n" +
                                "Este canto rebel que o passado\n" +
                                "Vem remir dos mais torpes labéus!\n" +
                                "Seja um hino de glória que fale\n" +
                                "De esperança, de um novo porvir!...\n ")
                        imgMusica.visibility = View.VISIBLE
                        imgMusica.setImageResource(R.drawable.republica)
                        notificacao()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                imgMusica.visibility = View.GONE
                txtResumo.text = ""
            }
        }

    }
    /**
     *  Trabalhando com notificações simples
     */
    fun notificacao(){
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val mNotification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, channelId)
        } else {
            Notification.Builder(this)
        }.apply {
            setContentIntent(pendingIntent)
            // adicionando um ícone na notificação
            setSmallIcon(R.drawable.notification_icon_background)
            setAutoCancel(true)
            // título da notificação
            setContentTitle(sMusica.selectedItem.toString())
            val i = sMusica.selectedItemPosition

            // cria um array para controlar os metadados de cana hino. Nesta lógica, os metadados devem ser inseridos na mesma sequência que seu respectivo
            var metadataMusicas = arrayOf(
                    "",
                    "Poema: Joaquim Osório Duque Estrada | Música: Francisco Manuel da Silva | Banda: 24º Batalhão de Caçadores",
                    "Letra: Olavo Bilac | Música: Francisco Braga | Banda: 24º Batalhão de Caçadores",
                    "Letra: Evaristo Ferreira da Veiga | Música: D. Pedro I | Banda: 24º Batalhão de Caçadores",
                    "Letra: Medeiros e Albuquerque | Música: Leopoldo Augusto Miguez")

            // mensagem da notificação
            setContentText(metadataMusicas[i])
        }.build()
        val mNotificationId: Int = 1000
        val nManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        nManager.notify(mNotificationId, mNotification)
    }
}
