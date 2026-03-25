# Cancel Booking

## Overview
_This endpoint allows customers to cancel an existing booking. Cancellation is only permitted for bookings in `PENDING` or `CONFIRMED` status._

|Method   |Path                                   |Exposed|
|---      |---                                    |---    |
| DELETE  | /api/bookings/{bookingReference}      | ✅    |

## Path Parameters

| Parameter        | Type   | Required | Description                                     |
|---               |---     |---       |---                                              |
| bookingReference | String | Yes      | Unique booking reference (e.g. `BKG-A1B2C3D4`) |

## JSON

### Request
<details>
  <summary>Request</summary>

```
DELETE /api/bookings/BKG-A1B2C3D4
```
</details>

### Response
<details>
  <summary>Response (200 OK)</summary>

```json
{
  "bookingId": 0,
  "bookingReference": "BKG-A1B2C3D4",
  "customerName": "string",
  "showId": 0,
  "seatIdentifiers": ["string"],
  "totalAmount": 0.00,
  "status": "CANCELLED",
  "paymentStatus": "COMPLETED",
  "bookedAt": "2026-03-25T10:00:00",
  "expiresAt": null
}
```
</details>

### Error Responses

| Status | Reason                                                        |
|---     |---                                                            |
| 404    | Booking reference not found                                   |
| 400    | Booking is already `CANCELLED` or `COMPLETED`, cannot cancel  |
