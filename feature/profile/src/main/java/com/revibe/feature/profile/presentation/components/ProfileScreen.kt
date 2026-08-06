package com.revibe.feature.profile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(
    userName: String,
    onLogoutClick: () -> Unit = {},
    onBonusCardClick: () -> Unit = {},
    onHistoryClick: () -> Unit = {},
    onCurrentBookingsClick: () -> Unit = {},
    onEditProfileClick: () -> Unit = {},
    onDeleteProfileClick: () -> Unit = {},

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F7))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ReVibe",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Выход",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.clickable { onLogoutClick() }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {

            Column {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ProfileButton(
                text = "Бонусная карта",
                color = Color(0xFF8B7CF6),
                onClick = onBonusCardClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProfileButton(
                text = "История покупок",
                color = Color.White,
                border = true,
                onClick = onHistoryClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProfileButton(
                text = "Текущие бронирования",
                color = Color.White,
                border = true,
                subtitle = "Получите товары по QR-коду\nили коду в SMS",
                onClick = onCurrentBookingsClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProfileButton(
                text = "Редактировать профиль",
                color = Color.Black,
                textColor = Color.White,
                onClick = onEditProfileClick
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProfileButton(
                text = "Удалить профиль",
                color = Color.Black,
                textColor = Color.White,
                onClick = onDeleteProfileClick
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}