# Browse Movies

## Overview
_This endpoint allows end customers to search and discover movies available across different cities, languages, and genres on the Cinebook platform._

|Method  |Path                   |Exposed|
|---     |---                    |---    |
| GET    | /api/browse/movies    | ✅    |

## Query Parameters

| Parameter  | Type   | Required | Description           |
|---         |---     |---       |---                    |
| city       | String | No       | Filter by city name   |
| language   | String | No       | Filter by language    |
| genre      | String | No       | Filter by genre       |

## JSON

### Request
<details>
  <summary>Request</summary>

```
GET /api/browse/movies?city=Mumbai&language=Hindi&genre=Action
```
</details>

### Response
<details>
  <summary>Response</summary>

```json
{
  "filters": {
    "city": "Mumbai",
    "language": "Hindi",
    "genre": "Action"
  },
  "movies": [
    {
      "movieId": 1,
      "title": "string",
      "language": "Hindi",
      "genre": "Action",
      "rating": 8.5,
      "durationMinutes": 150,
      "posterUrl": "string"
    }
  ]
}
```
</details>
