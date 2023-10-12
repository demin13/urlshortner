import React, {useState} from 'react';
import {TextField, Button, Container, Grid} from '@mui/material';
import { postApiRequest } from './apiService';
import {DesktopDatePicker} from "@mui/x-date-pickers/DesktopDatePicker";
import {AdapterDateFns} from "@mui/x-date-pickers/AdapterDateFns";
import {LocalizationProvider} from "@mui/x-date-pickers/LocalizationProvider";
import format from 'date-fns/format';
import { Link } from "react-router-dom";


const styles = {
    container: {
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: '100vh',
        padding: '16px',
    },
    header: {
        fontSize: '24px',
        marginBottom: '16px',
    },
    inputContainer: {
        marginBottom: '16px'
    },
    buttonContainer: {
        display: 'flex',
        gap: '16px',
    },
    responseText: {
        marginTop: '16px',
        textAlign: 'center',
    },
};

function UrlShorterHomePage() {
    const [response, setResponse] = useState({});
    const [selectedDate, handleDateChange] = useState(new Date());
    const [urlData, setUrlData] = useState({
        originalUrl: "",
    });

    const handleInputChange = (e) => {
        const {name, value} = e.target;
        setUrlData({...urlData, [name]: value});
    };

    const handlePostClick = (data) => {
        postApiRequest(data)
            .then((response) => {
                if (response.status === 200) {
                    setResponse({
                        "shortenUrl": `http://localhost:3000/${response.data.shortenedUrl}`,
                        "statusCode": `${response.status}`
                    });
                }
            })
            .catch((error) => {
                setResponse(`${error.message}`);
            });
    };

    const handleSubmit = () => {
        setUrlData({
            originalUrl: urlData.originalUrl,
        });
        let formattedDate = format(selectedDate, 'yyyy-MM-dd');
        const updatedUrlWithDate = {
            ...urlData,
            expiryDate: formattedDate,
        };
        handlePostClick(updatedUrlWithDate);
    };

    const convertToShortenCode = (shortenUrl) => {
        const segments = shortenUrl.split('/').filter(Boolean);
        return segments[segments.length - 1];
    };


    return (
        <Container style={styles.container}>
            <div style={styles.header}>
                URL SHORTNER
            </div>
            <div>
                <Grid container style={styles.inputContainer}>
                    <Grid item xs={12} sm={12}>
                        <TextField
                            name="originalUrl"
                            label="Enter Valid Long URL"
                            variant="outlined"
                            fullWidth
                            margin="normal"
                            value={urlData.originalUrl}
                            onChange={handleInputChange}
                            required
                        />
                    </Grid>

                </Grid>
                <Grid container style={styles.inputContainer}>
                    <Grid item xs={12} sm={12}>
                        <LocalizationProvider dateAdapter={AdapterDateFns}>
                            <DesktopDatePicker
                                label="Expiry Date"
                                value={selectedDate}
                                onChange={handleDateChange}
                                renderInput={(params) => <TextField {...params} margin="normal"/>}
                                required
                            />
                        </LocalizationProvider>
                    </Grid>
                </Grid>
            </div>
            <div style={styles.buttonContainer}>
                <Button
                    variant="contained"
                    color="primary"
                    onClick={handleSubmit}
                >
                    Short URL
                </Button>
            </div>
            <div style={styles.responseText}>
                <Link to={convertToShortenCode(`${response.shortenUrl}`)} target="_blank" rel="noopener noreferrer">
                    {response.shortenUrl}
                </Link>
            </div>
        </Container>
    );
}

export default UrlShorterHomePage;
