# Browse Theaters

## Overview
_This endpoint returns all theatres available in a given city, allowing customers to explore venue options before selecting a movie or show._

|Method  |Path                     |Exposed|
|---     |---                      |---    |
| GET    | /api/browse/theaters    | ✅    |

## Query Parameters

| Parameter | Type   | Required | Description      |
|---        |---     |---       |---               |
| city      | String | Yes      | City name        |

## JSON

### Request
<details>
  <summary>Request</summary>

```
GET /api/browse/theaters?city=Mumbai
```
</details>

### Response
<details>
  <summary>Response</summary>

```json
{
  "city": "Mumbai",
  "theaters": [
    {
      "theaterId": 1,
      "theaterName": "string",
      "address": "string",
      "locality": "string",
      "totalScreens": 5,
      "amenities": ["Parking", "Food Court", "Wheelchair Access"]
    }
  ]
}
```
</details>
