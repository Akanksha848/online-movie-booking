# Browse Shows

## Overview
_This endpoint allows end customers to browse theatres currently running a selected movie in a chosen city, including all available show timings for a specific date._

|Method  |Path                   |Exposed|
|---     |---                    |---    |
| GET    | /api/browse/shows     | ✅    |

## Query Parameters

| Parameter | Type       | Required | Description                        |
|---        |---         |---       |---                                 |
| movieId   | Long       | Yes      | ID of the movie                    |
| city      | String     | Yes      | City to search theatres in         |
| date      | LocalDate  | Yes      | Show date in `YYYY-MM-DD` format   |

## JSON

### Request
<details>
  <summary>Request</summary>

```
GET /api/browse/shows?movieId=101&city=Mumbai&date=2026-03-25
```
</details>

### Response
<details>
  <summary>Response</summary>

```json
{
  "movieId": 101,
  "city": "Mumbai",
  "date": "2026-03-25",
  "theaters": [
    {
      "theaterId": 1,
      "theaterName": "string",
      "address": "string",
      "shows": [
        {
          "showId": 201,
          "showTime": "10:00",
          "format": "2D",
          "language": "Hindi",
          "availableSeats": 120,
          "pricePerSeat": 200.00
        },
        {
          "showId": 202,
          "showTime": "14:30",
          "format": "3D",
          "language": "Hindi",
          "availableSeats": 80,
          "pricePerSeat": 300.00
        }
      ]
    }
  ]
}
```
</details>
