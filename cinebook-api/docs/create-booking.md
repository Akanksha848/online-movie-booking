# Create Booking

## Overview
_This endpoint allows end customers to book movie tickets by selecting a theatre, show timing, and preferred seats. On success, payment is processed and a booking confirmation is returned._

|Method  |Path               |Exposed|
|---     |---                |---    |
| POST   | /api/bookings     | ✅    |

## JSON

### Request
<details>
  <summary>Request</summary>

```json
{
  "customerName": "string",
  "customerEmail": "string",
  "customerPhone": "string",
  "showId": 0,
  "seatIds": [0],
  "paymentMethod": "string"
}
```
</details>

### Response
<details>
  <summary>Response (201 Created)</summary>

```json
{
  "bookingId": 0,
  "bookingReference": "string",
  "customerName": "string",
  "showId": 0,
  "seatIdentifiers": ["string"],
  "totalAmount": 0.00,
  "status": "CONFIRMED",
  "paymentStatus": "COMPLETED",
  "bookedAt": "2026-03-25T10:00:00",
  "expiresAt": null
}
```
</details>

### Error Responses

| Status | Reason                                     |
|---     |---                                         |
| 400    | Validation failed (missing or invalid fields) |
| 500    | Payment failed or booking could not be completed |
