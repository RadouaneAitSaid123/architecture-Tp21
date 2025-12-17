import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { Save, ArrowLeft } from 'lucide-react';
import api from '../services/api';

const ReservationForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const isEdit = !!id;

    const [formData, setFormData] = useState({
        clientId: 1, // Default mock client
        chambreId: 1, // Default mock room
        dateDebut: '',
        dateFin: '',
        preferences: ''
    });

    useEffect(() => {
        if (isEdit) {
            api.get(`/${id}`).then((res) => {
                const data = res.data;
                setFormData({
                    clientId: data.client?.id || 1,
                    chambreId: data.chambre?.id || 1,
                    dateDebut: data.dateDebut,
                    dateFin: data.dateFin,
                    preferences: data.preferences
                });
            });
        }
    }, [id, isEdit]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        const payload = {
            client: { id: formData.clientId },
            chambre: { id: formData.chambreId },
            dateDebut: formData.dateDebut,
            dateFin: formData.dateFin,
            preferences: formData.preferences
        };

        try {
            if (isEdit) {
                await api.put(`/${id}`, payload);
            } else {
                await api.post('', payload);
            }
            navigate('/');
        } catch (error) {
            alert('Error saving reservation');
            console.error(error);
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    return (
        <div className="container" style={{ maxWidth: '600px' }}>
            <button onClick={() => navigate('/')} className="btn" style={{ background: 'transparent', color: 'var(--text-muted)', marginBottom: '1rem', paddingLeft: 0 }}>
                <ArrowLeft size={18} /> Back to dashboard
            </button>

            <div className="card glass-panel">
                <h2 style={{ fontSize: '1.5rem', fontWeight: 'bold', marginBottom: '1.5rem' }}>
                    {isEdit ? 'Edit Reservation' : 'New Reservation'}
                </h2>

                <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1.5rem' }}>
                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem', fontWeight: '500' }}>Client ID (Mock)</label>
                        <input
                            name="clientId"
                            type="number"
                            value={formData.clientId}
                            onChange={handleChange}
                            className="input"
                            required
                        />
                    </div>

                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem', fontWeight: '500' }}>Room ID (Mock)</label>
                        <input
                            name="chambreId"
                            type="number"
                            value={formData.chambreId}
                            onChange={handleChange}
                            className="input"
                            required
                        />
                    </div>

                    <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
                        <div>
                            <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem', fontWeight: '500' }}>Check-in</label>
                            <input
                                name="dateDebut"
                                type="date"
                                value={formData.dateDebut}
                                onChange={handleChange}
                                className="input"
                                required
                            />
                        </div>
                        <div>
                            <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem', fontWeight: '500' }}>Check-out</label>
                            <input
                                name="dateFin"
                                type="date"
                                value={formData.dateFin}
                                onChange={handleChange}
                                className="input"
                                required
                            />
                        </div>
                    </div>

                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem', fontSize: '0.875rem', fontWeight: '500' }}>Preferences</label>
                        <textarea
                            name="preferences"
                            value={formData.preferences}
                            onChange={handleChange}
                            className="input"
                            rows="3"
                        />
                    </div>

                    <button type="submit" className="btn btn-primary" style={{ justifyContent: 'center', marginTop: '1rem' }}>
                        <Save size={18} /> Save Reservation
                    </button>
                </form>
            </div>
        </div>
    );
};

export default ReservationForm;
